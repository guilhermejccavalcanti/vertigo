/*
 * Copyright 2013-2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.kuujo.vertigo.integration.cluster;

import static org.vertx.testtools.VertxAssert.assertEquals;
import static org.vertx.testtools.VertxAssert.assertTrue;
import static org.vertx.testtools.VertxAssert.testComplete;

import java.util.Collection;

import net.kuujo.vertigo.Vertigo;
import net.kuujo.vertigo.cluster.Cluster;
import net.kuujo.vertigo.java.ComponentVerticle;
import net.kuujo.vertigo.network.ActiveNetwork;
import net.kuujo.vertigo.network.NetworkConfig;

import org.junit.AfterClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.model.InitializationError;
import org.vertx.java.core.AsyncResult;
import org.vertx.java.core.Handler;
import org.vertx.java.core.json.JsonArray;
import org.vertx.java.core.json.JsonObject;
import org.vertx.testtools.JavaClassRunner;
import org.vertx.testtools.TestVerticle;

/**
 * A remote cluster test.
 *
 * @author Jordan Halterman
 */
@RunWith(ClusterManagerTest.ClusterManagerClassRunner.class)
public class ClusterManagerTest extends TestVerticle {

  public static class ClusterManagerClassRunner extends JavaClassRunner {
    static {
      System.setProperty("vertx.mods", "src/test/resources/test-mods");
    }
    public ClusterManagerClassRunner(Class<?> klass) throws InitializationError {
      super(klass);
    }
  }

  @Test
  public void testDeploy() {
    final Vertigo vertigo = new Vertigo(this);
    vertigo.deployCluster(new Handler<AsyncResult<Cluster>>() {
      @Override
      public void handle(AsyncResult<Cluster> result) {
        assertTrue(result.succeeded());
        NetworkConfig network = vertigo.createNetwork("test-deploy");
        network.addVerticle("feeder", TestFeeder.class.getName());
        network.addVerticle("worker1", TestWorker.class.getName(), 2);
        network.createConnection("feeder", "stream1", "worker", "stream1");
        network.createConnection("feeder", "stream2", "worker", "stream2");

        final Cluster cluster = result.result();
        cluster.deployNetwork(network, new Handler<AsyncResult<ActiveNetwork>>() {
          @Override
          public void handle(AsyncResult<ActiveNetwork> result) {
            assertTrue(result.succeeded());
            testComplete();
          }
        });
      }
    });
  }

  @Test
  public void testDeployFromJson() {
    final Vertigo vertigo = new Vertigo(this);
    vertigo.deployCluster(new Handler<AsyncResult<Cluster>>() {
      @Override
      public void handle(AsyncResult<Cluster> result) {
        assertTrue(result.succeeded());
        JsonObject network = new JsonObject()
            .putString("name", "test-deploy-from-json")
            .putObject("components", new JsonObject()
                .putObject("feeder", new JsonObject()
                    .putString("type", "verticle")
                    .putString("main", TestFeeder.class.getName()))
                .putObject("worker", new JsonObject()
                    .putString("type", "verticle")
                    .putString("main", TestWorker.class.getName())
                    .putNumber("instances", 2)))
            .putArray("connections", new JsonArray().add(new JsonObject()
                .putObject("source", new JsonObject()
                    .putString("component", "feeder")
                    .putString("port", "out"))
                .putObject("target", new JsonObject()
                    .putString("component", "worker")
                    .putString("port", "in"))));

        final Cluster cluster = result.result();
        cluster.deployNetwork(network, new Handler<AsyncResult<ActiveNetwork>>() {
          @Override
          public void handle(AsyncResult<ActiveNetwork> result) {
            assertTrue(result.succeeded());
            testComplete();
          }
        });
      }
    });
  }

  @Test
  public void testUndeployByName() {
    final Vertigo vertigo = new Vertigo(this);
    vertigo.deployCluster(new Handler<AsyncResult<Cluster>>() {
      @Override
      public void handle(AsyncResult<Cluster> result) {
        assertTrue(result.succeeded());
        NetworkConfig network = vertigo.createNetwork("test-undeploy-by-name");
        network.addVerticle("feeder", TestFeeder.class.getName());
        network.addVerticle("worker1", TestWorker.class.getName(), 2);
        network.createConnection("feeder", "stream1", "worker", "stream1");
        network.addVerticle("worker2", TestWorker.class.getName(), 2);
        network.createConnection("feeder", "stream2", "worker", "stream2");

        final Cluster cluster = result.result();
        cluster.deployNetwork(network, new Handler<AsyncResult<ActiveNetwork>>() {
          @Override
          public void handle(AsyncResult<ActiveNetwork> result) {
            assertTrue(result.succeeded());
            vertx.setTimer(2000, new Handler<Long>() {
              @Override
              public void handle(Long timerID) {
                cluster.undeployNetwork("test-undeploy-by-name", new Handler<AsyncResult<Void>>() {
                  @Override
                  public void handle(AsyncResult<Void> result) {
                    assertTrue(result.succeeded());
                    testComplete();
                  }
                });
              }
            });
          }
        });
      }
    });
  }

  @Test
  public void testUndeployByConfig() {
    final Vertigo vertigo = new Vertigo(this);
    vertigo.deployCluster(new Handler<AsyncResult<Cluster>>() {
      @Override
      public void handle(AsyncResult<Cluster> result) {
        assertTrue(result.succeeded());
        final NetworkConfig network = vertigo.createNetwork("test-undeploy-by-config");
        network.addVerticle("feeder", TestFeeder.class.getName());
        network.addVerticle("worker1", TestWorker.class.getName(), 2);
        network.createConnection("feeder", "stream1", "worker", "stream1");
        network.addVerticle("worker2", TestWorker.class.getName(), 2);
        network.createConnection("feeder", "stream2", "worker", "stream2");

        final Cluster cluster = result.result();
        cluster.deployNetwork(network, new Handler<AsyncResult<ActiveNetwork>>() {
          @Override
          public void handle(AsyncResult<ActiveNetwork> result) {
            assertTrue(result.succeeded());
            vertx.setTimer(2000, new Handler<Long>() {
              @Override
              public void handle(Long timerID) {
                cluster.undeployNetwork(network, new Handler<AsyncResult<Void>>() {
                  @Override
                  public void handle(AsyncResult<Void> result) {
                    assertTrue(result.succeeded());
                    testComplete();
                  }
                });
              }
            });
          }
        });
      }
    });
  }

  @Test
  public void testGetNetwork() {
    final Vertigo vertigo = new Vertigo(this);
    vertigo.deployCluster(new Handler<AsyncResult<Cluster>>() {
      @Override
      public void handle(AsyncResult<Cluster> result) {
        assertTrue(result.succeeded());
        NetworkConfig network = vertigo.createNetwork("test-get-network-1");
        network.addVerticle("feeder", TestFeeder.class.getName());
        network.addVerticle("worker", TestWorker.class.getName(), 2);
        network.createConnection("feeder", "stream", "worker", "stream");

        final Cluster cluster = result.result();
        cluster.deployNetwork(network, new Handler<AsyncResult<ActiveNetwork>>() {
          @Override
          public void handle(AsyncResult<ActiveNetwork> result) {
            assertTrue(result.succeeded());
            NetworkConfig network = vertigo.createNetwork("test-get-network-2");
            network.addVerticle("feeder", TestFeeder.class.getName());
            network.addVerticle("worker", TestWorker.class.getName(), 2);
            network.createConnection("feeder", "stream", "worker", "stream");
            cluster.deployNetwork(network, new Handler<AsyncResult<ActiveNetwork>>() {
              @Override
              public void handle(AsyncResult<ActiveNetwork> result) {
                assertTrue(result.succeeded());
                cluster.getNetwork("test-get-network-1", new Handler<AsyncResult<ActiveNetwork>>() {
                  @Override
                  public void handle(AsyncResult<ActiveNetwork> result) {
                    assertTrue(result.succeeded());
                    assertEquals("test-get-network-1", result.result().getConfig().getName());
                    testComplete();
                  }
                });
              }
            });
          }
        });
      }
    });
  }

  @Test
  public void testGetNetworks() {
    final Vertigo vertigo = new Vertigo(this);
    vertigo.deployCluster(new Handler<AsyncResult<Cluster>>() {
      @Override
      public void handle(AsyncResult<Cluster> result) {
        assertTrue(result.succeeded());
        NetworkConfig network = vertigo.createNetwork("test-get-networks-1");
        network.addVerticle("feeder", TestFeeder.class.getName());
        network.addVerticle("worker", TestWorker.class.getName(), 2);
        network.createConnection("feeder", "stream", "worker", "stream");

        final Cluster cluster = result.result();
        cluster.deployNetwork(network, new Handler<AsyncResult<ActiveNetwork>>() {
          @Override
          public void handle(AsyncResult<ActiveNetwork> result) {
            assertTrue(result.succeeded());
            NetworkConfig network = vertigo.createNetwork("test-get-networks-2");
            network.addVerticle("feeder", TestFeeder.class.getName());
            network.addVerticle("worker", TestWorker.class.getName(), 2);
            network.createConnection("feeder", "stream", "worker", "stream");
            cluster.deployNetwork(network, new Handler<AsyncResult<ActiveNetwork>>() {
              @Override
              public void handle(AsyncResult<ActiveNetwork> result) {
                assertTrue(result.succeeded());
                cluster.getNetworks(new Handler<AsyncResult<Collection<ActiveNetwork>>>() {
                  @Override
                  public void handle(AsyncResult<Collection<ActiveNetwork>> result) {
                    assertTrue(result.succeeded());
                    assertEquals(2, result.result().size());
                    testComplete();
                  }
                });
              }
            });
          }
        });
      }
    });
  }

  @Test
  public void testDeployWithCircularConnections() {
    final Vertigo vertigo = new Vertigo(this);
    vertigo.deployCluster(new Handler<AsyncResult<Cluster>>() {
      @Override
      public void handle(AsyncResult<Cluster> result) {
        assertTrue(result.succeeded());
        NetworkConfig network = vertigo.createNetwork("test-circular-deploy");
        network.addVerticle("feeder", TestFeeder.class.getName());
        network.addVerticle("worker", TestWorker.class.getName());
        network.createConnection("feeder", "out", "worker", "in");
        network.createConnection("worker", "out", "feeder", "in");

        Cluster cluster = result.result();
        cluster.deployNetwork(network, new Handler<AsyncResult<ActiveNetwork>>() {
          @Override
          public void handle(AsyncResult<ActiveNetwork> result) {
            assertTrue(result.succeeded());
            testComplete();
          }
        });
      }
    });
  }

  @Test
  public void testDeployWithBrokenConnection() {
    final Vertigo vertigo = new Vertigo(this);
    vertigo.deployCluster(new Handler<AsyncResult<Cluster>>() {
      @Override
      public void handle(AsyncResult<Cluster> result) {
        assertTrue(result.succeeded());
        NetworkConfig network = vertigo.createNetwork("test-broken-deploy");
        network.addVerticle("feeder", TestFeeder.class.getName());
        network.addVerticle("worker", TestWorker.class.getName(), 2);
        network.createConnection("feeder", "stream", "worker", "stream");
        network.createConnection("worker", "out", "nowhere", "in");

        Cluster cluster = result.result();
        cluster.deployNetwork(network, new Handler<AsyncResult<ActiveNetwork>>() {
          @Override
          public void handle(AsyncResult<ActiveNetwork> result) {
            assertTrue(result.succeeded());
            testComplete();
          }
        });
      }
    });
  }

  public static class TestFeeder extends ComponentVerticle {
  }

  public static class TestWorker extends ComponentVerticle {
  }

  @Test
  public void testDeployMerge() {
    final Vertigo vertigo = new Vertigo(this);
    vertigo.deployCluster(new Handler<AsyncResult<Cluster>>() {
      @Override
      public void handle(AsyncResult<Cluster> result) {
        assertTrue(result.succeeded());
        NetworkConfig network = vertigo.createNetwork("test-deploy-merge");
        network.addVerticle("feeder", TestFeeder.class.getName());
        network.addVerticle("worker", TestWorker.class.getName());
        network.createConnection("feeder", "out", "worker", "in");

        final Cluster cluster = result.result();
        cluster.deployNetwork(network, new Handler<AsyncResult<ActiveNetwork>>() {
          @Override
          public void handle(AsyncResult<ActiveNetwork> result) {
            assertTrue(result.succeeded());
            NetworkConfig network = vertigo.createNetwork("test-deploy-merge");
            network.addVerticle("feeder2", TestFeeder.class.getName());
            network.addVerticle("worker2", TestWorker.class.getName());
            network.createConnection("feeder2", "out", "worker2", "in");

            cluster.deployNetwork(network, new Handler<AsyncResult<ActiveNetwork>>() {
              @Override
              public void handle(AsyncResult<ActiveNetwork> result) {
                assertTrue(result.succeeded());
                testComplete();
              }
            });
          }
        });
      }
    });
  }

  @AfterClass
  public static void after() {
    System.clearProperty("vertx.mods");
  }

}

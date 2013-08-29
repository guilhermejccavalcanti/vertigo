/*
* Copyright 2013 the original author or authors.
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
package com.blankstyle.vine;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.vertx.java.core.AsyncResult;
import org.vertx.java.core.AsyncResultHandler;
import org.vertx.java.core.Future;
import org.vertx.java.core.Handler;
import org.vertx.java.core.eventbus.Message;
import org.vertx.java.core.impl.DefaultFutureResult;
import org.vertx.java.core.json.JsonObject;

import com.blankstyle.vine.context.ConnectionContext;
import com.blankstyle.vine.context.WorkerContext;
import com.blankstyle.vine.eventbus.ReliableBusVerticle;
import com.blankstyle.vine.eventbus.ReliableEventBus;
import com.blankstyle.vine.messaging.ConnectionPool;
import com.blankstyle.vine.messaging.DefaultChannel;
import com.blankstyle.vine.messaging.DefaultConnectionPool;
import com.blankstyle.vine.messaging.Dispatcher;
import com.blankstyle.vine.messaging.Groupings;
import com.blankstyle.vine.messaging.JsonMessage;
import com.blankstyle.vine.messaging.ReliableChannel;
import com.blankstyle.vine.messaging.ReliableEventBusConnection;

/**
 * A core seed verticle.
 *
 * @author Jordan Halterman
 */
public abstract class SeedVerticle extends ReliableBusVerticle implements Handler<Message<JsonObject>> {

  protected ReliableEventBus eventBus;

  protected WorkerContext context;

  protected Collection<ReliableChannel> channels;

  private JsonMessage currentMessage;

  private String seedName;

  @Override
  protected void start(ReliableEventBus eventBus) {
    this.eventBus = eventBus;
    setupContext();
    setupChannels();
    eventBus.registerHandler(getMandatoryStringConfig("address"), this);
  }

  /**
   * Sets up the seed context.
   */
  private void setupContext() {
    this.context = new WorkerContext(config);
    this.seedName = context.getContext().getDefinition().getName();
  }

  /**
   * Sets up seed channels.
   */
  private void setupChannels() {
    channels = new ArrayList<ReliableChannel>();
    Collection<ConnectionContext> connections = context.getContext().getConnectionContexts();
    Iterator<ConnectionContext> iter = connections.iterator();
    while (iter.hasNext()) {
      ConnectionContext connectionContext = iter.next();
      try {
        Dispatcher dispatcher = Groupings.get(connectionContext.getGrouping()).newInstance();
        ConnectionPool connectionPool = new DefaultConnectionPool();
        String[] addresses = connectionContext.getAddresses();
        for (String address : addresses) {
          connectionPool.add(new ReliableEventBusConnection(address, eventBus));
        }
        dispatcher.init(connectionPool);
        channels.add(new DefaultChannel(dispatcher));
      }
      catch (InstantiationException | IllegalAccessException e) {
        container.logger().error("Failed to find grouping handler.");
      }
      
    }
  }

  @Override
  public void handle(Message<JsonObject> message) {
    String action = getMandatoryString("action", message);
    if (action == null) {
      this.sendError(message, "No action specified.");
    }

    switch (action) {
      case "receive":
        doReceive(message);
        break;
      default:
        sendError(message, String.format("Invalid action %s.", action));
    }
  }

  /**
   * Handles receiving a message for processing.
   */
  private void doReceive(Message<JsonObject> message) {
    message.reply();
    currentMessage = new JsonMessage(message.body());
    JsonObject body = getMandatoryObject("body", message);
    process(body.copy());
  }

  /**
   * Processes a JSON message.
   *
   * @param data
   *   The data to process.
   */
  protected abstract void process(JsonObject data);

  /**
   * Emits a JSON message.
   *
   * @param data
   *   The data to emit.
   */
  protected void emit(JsonObject data) {
    Iterator<ReliableChannel> iter = channels.iterator();
    if (iter.hasNext()) {
      recursiveEmit(data, iter.next(), iter);
    }
  }

  /**
   * Recursively emits data to a list of channels.
   *
   * @param data
   *   The data to emit.
   * @param channel
   *   The current channel to which to emit.
   * @param channelIterator
   *   An iterator over all channels.
   */
  private void recursiveEmit(final JsonObject data, ReliableChannel currentChannel, final Iterator<ReliableChannel> channelIterator) {
    JsonMessage newMessage = currentMessage.copy();
    newMessage.setBody(data).tag(seedName);
    currentChannel.publish(newMessage, new AsyncResultHandler<Void>() {
      @Override
      public void handle(AsyncResult<Void> result) {
        if (channelIterator.hasNext()) {
          recursiveEmit(data, channelIterator.next(), channelIterator);
        }
      }
    });
  }

  /**
   * Emits a JSON message.
   *
   * @param data
   *   The data to emit.
   * @param doneHandler
   *   A handler to be invoked once complete.
   */
  protected void emit(JsonObject data, Handler<AsyncResult<Void>> doneHandler) {
    Iterator<ReliableChannel> iter = channels.iterator();
    if (iter.hasNext()) {
      recursiveEmit(data, iter.next(), iter, doneHandler);
    }
  }

  /**
   * Recursively emits data to a list of channels, invoking a handler when complete.
   *
   * @param data
   *   The data to emit.
   * @param currentChannel
   *   The current channel to which to emit.
   * @param channelIterator
   *   An iterator over all channels.
   * @param doneHandler
   *   A handler to be invoked once all channels have received the message.
   */
  private void recursiveEmit(final JsonObject data, ReliableChannel currentChannel, final Iterator<ReliableChannel> channelIterator, final Handler<AsyncResult<Void>> doneHandler) {
    final Future<Void> future = new DefaultFutureResult<Void>().setHandler(doneHandler);
    JsonMessage newMessage = currentMessage.copy();
    newMessage.setBody(data).tag(seedName);
    currentChannel.publish(newMessage, new AsyncResultHandler<Void>() {
      @Override
      public void handle(AsyncResult<Void> result) {
        if (result.succeeded()) {
          if (channelIterator.hasNext()) {
            recursiveEmit(data, channelIterator.next(), channelIterator);
          }
          else {
            future.setResult(null);
          }
        }
        else {
          future.setFailure(result.cause());
        }
      }
    });
  }

}

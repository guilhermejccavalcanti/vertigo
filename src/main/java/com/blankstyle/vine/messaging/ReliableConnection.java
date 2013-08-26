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
package com.blankstyle.vine.messaging;

import org.vertx.java.core.AsyncResultHandler;
import org.vertx.java.core.buffer.Buffer;
import org.vertx.java.core.eventbus.Message;
import org.vertx.java.core.json.JsonArray;
import org.vertx.java.core.json.JsonObject;

/**
 * A reliable point-to-point connection
 *
 * @author Jordan Halterman
 */
public interface ReliableConnection extends Connection {

  /**
   * Sends a message with a default timeout.
   *
   * @param message
   *   The message.
   * @param replyHandler
   *   An asynchronous reply handler. This will be invoked with the message reply,
   *   or with a TimeoutException if the response times out.
   */
  public Connection send(Object message, @SuppressWarnings("rawtypes") AsyncResultHandler<Message> replyHandler);

  /**
   * Sends a message with a timeout.
   *
   * @param message
   *   The message.
   * @param timeout
   *   The message timeout.
   * @param replyHandler
   *   An asynchronous reply handler. This will be invoked with the message reply,
   *   or with a TimeoutException if the response times out.
   */
  public Connection send(Object message, long timeout, @SuppressWarnings("rawtypes") AsyncResultHandler<Message> replyHandler);

  /**
   * Sends a message with a timeout, attempting to re-send the message if a timeout occurs.
   *
   * @param message
   *   The message.
   * @param timeout
   *   The message timeout.
   * @param retry
   *   A boolean indicating whether to attempt to re-send messages.
   * @param replyHandler
   *   An asynchronous reply handler. This will be invoked with the message reply,
   *   or with a TimeoutException if the response times out.
   * @return
   *   The called Connection instance.
   */
  public Connection send(Object message, long timeout, boolean retry, @SuppressWarnings("rawtypes") AsyncResultHandler<Message> replyHandler);

  /**
   * Sends a message with a timeout, attempting to re-send the message if a timeout occurs.
   *
   * @param message
   *   The message.
   * @param timeout
   *   The message timeout.
   * @param retry
   *   A boolean indicating whether to attempt to re-send messages.
   * @param attempts
   *   The number of re-send attempts allowed.
   * @param replyHandler
   *   An asynchronous reply handler. This will be invoked with the message reply,
   *   or with a TimeoutException if the response times out.
   * @return
   *   The called Connection instance.
   */
  public Connection send(Object message, long timeout, boolean retry, int attempts, @SuppressWarnings("rawtypes") AsyncResultHandler<Message> replyHandler);

  /**
   * Sends a message with a default timeout.
   *
   * @param message
   *   The message.
   * @param replyHandler
   *   An asynchronous reply handler. This will be invoked with the message reply,
   *   or with a TimeoutException if the response times out.
   */
  public <T> Connection send(JsonObject message, AsyncResultHandler<Message<T>> replyHandler);

  /**
   * Sends a message with a timeout.
   *
   * @param message
   *   The message.
   * @param timeout
   *   The message timeout.
   * @param replyHandler
   *   An asynchronous reply handler. This will be invoked with the message reply,
   *   or with a TimeoutException if the response times out.
   */
  public <T> Connection send(JsonObject message, long timeout, AsyncResultHandler<Message<T>> replyHandler);

  /**
   * Sends a message with a timeout, attempting to re-send the message if a timeout occurs.
   *
   * @param message
   *   The message.
   * @param timeout
   *   The message timeout.
   * @param retry
   *   A boolean indicating whether to attempt to re-send messages.
   * @param replyHandler
   *   An asynchronous reply handler. This will be invoked with the message reply,
   *   or with a TimeoutException if the response times out.
   * @return
   *   The called Connection instance.
   */
  public <T> Connection send(JsonObject message, long timeout, boolean retry, AsyncResultHandler<Message<T>> replyHandler);

  /**
   * Sends a message with a timeout, attempting to re-send the message if a timeout occurs.
   *
   * @param message
   *   The message.
   * @param timeout
   *   The message timeout.
   * @param retry
   *   A boolean indicating whether to attempt to re-send messages.
   * @param attempts
   *   The number of re-send attempts allowed.
   * @param replyHandler
   *   An asynchronous reply handler. This will be invoked with the message reply,
   *   or with a TimeoutException if the response times out.
   * @return
   *   The called Connection instance.
   */
  public <T> Connection send(JsonObject message, long timeout, boolean retry, int attempts, AsyncResultHandler<Message<T>> replyHandler);

  /**
   * Sends a message with a default timeout.
   *
   * @param message
   *   The message.
   * @param replyHandler
   *   An asynchronous reply handler. This will be invoked with the message reply,
   *   or with a TimeoutException if the response times out.
   */
  public <T> Connection send(JsonArray message, AsyncResultHandler<Message<T>> replyHandler);

  /**
   * Sends a message with a timeout.
   *
   * @param message
   *   The message.
   * @param timeout
   *   The message timeout.
   * @param replyHandler
   *   An asynchronous reply handler. This will be invoked with the message reply,
   *   or with a TimeoutException if the response times out.
   */
  public <T> Connection send(JsonArray message, long timeout, AsyncResultHandler<Message<T>> replyHandler);

  /**
   * Sends a message with a timeout, attempting to re-send the message if a timeout occurs.
   *
   * @param message
   *   The message.
   * @param timeout
   *   The message timeout.
   * @param retry
   *   A boolean indicating whether to attempt to re-send messages.
   * @param replyHandler
   *   An asynchronous reply handler. This will be invoked with the message reply,
   *   or with a TimeoutException if the response times out.
   * @return
   *   The called Connection instance.
   */
  public <T> Connection send(JsonArray message, long timeout, boolean retry, AsyncResultHandler<Message<T>> replyHandler);

  /**
   * Sends a message with a timeout, attempting to re-send the message if a timeout occurs.
   *
   * @param message
   *   The message.
   * @param timeout
   *   The message timeout.
   * @param retry
   *   A boolean indicating whether to attempt to re-send messages.
   * @param attempts
   *   The number of re-send attempts allowed.
   * @param replyHandler
   *   An asynchronous reply handler. This will be invoked with the message reply,
   *   or with a TimeoutException if the response times out.
   * @return
   *   The called Connection instance.
   */
  public <T> Connection send(JsonArray message, long timeout, boolean retry, int attempts, AsyncResultHandler<Message<T>> replyHandler);

  /**
   * Sends a message with a default timeout.
   *
   * @param message
   *   The message.
   * @param replyHandler
   *   An asynchronous reply handler. This will be invoked with the message reply,
   *   or with a TimeoutException if the response times out.
   */
  public <T> Connection send(Buffer message, AsyncResultHandler<Message<T>> replyHandler);

  /**
   * Sends a message with a timeout.
   *
   * @param message
   *   The message.
   * @param timeout
   *   The message timeout.
   * @param replyHandler
   *   An asynchronous reply handler. This will be invoked with the message reply,
   *   or with a TimeoutException if the response times out.
   */
  public <T> Connection send(Buffer message, long timeout, AsyncResultHandler<Message<T>> replyHandler);

  /**
   * Sends a message with a timeout, attempting to re-send the message if a timeout occurs.
   *
   * @param message
   *   The message.
   * @param timeout
   *   The message timeout.
   * @param retry
   *   A boolean indicating whether to attempt to re-send messages.
   * @param attempts
   *   The number of re-send attempts allowed.
   * @param replyHandler
   *   An asynchronous reply handler. This will be invoked with the message reply,
   *   or with a TimeoutException if the response times out.
   * @return
   *   The called Connection instance.
   */
  public <T> Connection send(Buffer message, long timeout, boolean retry, AsyncResultHandler<Message<T>> replyHandler);

  /**
   * Sends a message with a timeout, attempting to re-send the message if a timeout occurs.
   *
   * @param message
   *   The message.
   * @param timeout
   *   The message timeout.
   * @param retry
   *   A boolean indicating whether to attempt to re-send messages.
   * @param attempts
   *   The number of re-send attempts allowed.
   * @param replyHandler
   *   An asynchronous reply handler. This will be invoked with the message reply,
   *   or with a TimeoutException if the response times out.
   * @return
   *   The called Connection instance.
   */
  public <T> Connection send(Buffer message, long timeout, boolean retry, int attempts, AsyncResultHandler<Message<T>> replyHandler);

  /**
   * Sends a message with a default timeout.
   *
   * @param message
   *   The message.
   * @param replyHandler
   *   An asynchronous reply handler. This will be invoked with the message reply,
   *   or with a TimeoutException if the response times out.
   */
  public <T> Connection send(byte[] message, AsyncResultHandler<Message<T>> replyHandler);

  /**
   * Sends a message with a timeout.
   *
   * @param message
   *   The message.
   * @param timeout
   *   The message timeout.
   * @param replyHandler
   *   An asynchronous reply handler. This will be invoked with the message reply,
   *   or with a TimeoutException if the response times out.
   */
  public <T> Connection send(byte[] message, long timeout, AsyncResultHandler<Message<T>> replyHandler);

  /**
   * Sends a message with a timeout, attempting to re-send the message if a timeout occurs.
   *
   * @param message
   *   The message.
   * @param timeout
   *   The message timeout.
   * @param retry
   *   A boolean indicating whether to attempt to re-send messages.
   * @param attempts
   *   The number of re-send attempts allowed.
   * @param replyHandler
   *   An asynchronous reply handler. This will be invoked with the message reply,
   *   or with a TimeoutException if the response times out.
   * @return
   *   The called Connection instance.
   */
  public <T> Connection send(byte[] message, long timeout, boolean retry, AsyncResultHandler<Message<T>> replyHandler);

  /**
   * Sends a message with a timeout, attempting to re-send the message if a timeout occurs.
   *
   * @param message
   *   The message.
   * @param timeout
   *   The message timeout.
   * @param retry
   *   A boolean indicating whether to attempt to re-send messages.
   * @param attempts
   *   The number of re-send attempts allowed.
   * @param replyHandler
   *   An asynchronous reply handler. This will be invoked with the message reply,
   *   or with a TimeoutException if the response times out.
   * @return
   *   The called Connection instance.
   */
  public <T> Connection send(byte[] message, long timeout, boolean retry, int attempts, AsyncResultHandler<Message<T>> replyHandler);

  /**
   * Sends a message with a default timeout.
   *
   * @param message
   *   The message.
   * @param replyHandler
   *   An asynchronous reply handler. This will be invoked with the message reply,
   *   or with a TimeoutException if the response times out.
   */
  public <T> Connection send(String message, AsyncResultHandler<Message<T>> replyHandler);

  /**
   * Sends a message with a timeout.
   *
   * @param message
   *   The message.
   * @param timeout
   *   The message timeout.
   * @param replyHandler
   *   An asynchronous reply handler. This will be invoked with the message reply,
   *   or with a TimeoutException if the response times out.
   */
  public <T> Connection send(String message, long timeout, AsyncResultHandler<Message<T>> replyHandler);

  /**
   * Sends a message with a timeout, attempting to re-send the message if a timeout occurs.
   *
   * @param message
   *   The message.
   * @param timeout
   *   The message timeout.
   * @param retry
   *   A boolean indicating whether to attempt to re-send messages.
   * @param attempts
   *   The number of re-send attempts allowed.
   * @param replyHandler
   *   An asynchronous reply handler. This will be invoked with the message reply,
   *   or with a TimeoutException if the response times out.
   * @return
   *   The called Connection instance.
   */
  public <T> Connection send(String message, long timeout, boolean retry, AsyncResultHandler<Message<T>> replyHandler);

  /**
   * Sends a message with a timeout, attempting to re-send the message if a timeout occurs.
   *
   * @param message
   *   The message.
   * @param timeout
   *   The message timeout.
   * @param retry
   *   A boolean indicating whether to attempt to re-send messages.
   * @param attempts
   *   The number of re-send attempts allowed.
   * @param replyHandler
   *   An asynchronous reply handler. This will be invoked with the message reply,
   *   or with a TimeoutException if the response times out.
   * @return
   *   The called Connection instance.
   */
  public <T> Connection send(String message, long timeout, boolean retry, int attempts, AsyncResultHandler<Message<T>> replyHandler);

  /**
   * Sends a message with a default timeout.
   *
   * @param message
   *   The message.
   * @param replyHandler
   *   An asynchronous reply handler. This will be invoked with the message reply,
   *   or with a TimeoutException if the response times out.
   */
  public <T> Connection send(Integer message, AsyncResultHandler<Message<T>> replyHandler);

  /**
   * Sends a message with a timeout.
   *
   * @param message
   *   The message.
   * @param timeout
   *   The message timeout.
   * @param replyHandler
   *   An asynchronous reply handler. This will be invoked with the message reply,
   *   or with a TimeoutException if the response times out.
   */
  public <T> Connection send(Integer message, long timeout, AsyncResultHandler<Message<T>> replyHandler);

  /**
   * Sends a message with a timeout, attempting to re-send the message if a timeout occurs.
   *
   * @param message
   *   The message.
   * @param timeout
   *   The message timeout.
   * @param retry
   *   A boolean indicating whether to attempt to re-send messages.
   * @param attempts
   *   The number of re-send attempts allowed.
   * @param replyHandler
   *   An asynchronous reply handler. This will be invoked with the message reply,
   *   or with a TimeoutException if the response times out.
   * @return
   *   The called Connection instance.
   */
  public <T> Connection send(Integer message, long timeout, boolean retry, AsyncResultHandler<Message<T>> replyHandler);

  /**
   * Sends a message with a timeout, attempting to re-send the message if a timeout occurs.
   *
   * @param message
   *   The message.
   * @param timeout
   *   The message timeout.
   * @param retry
   *   A boolean indicating whether to attempt to re-send messages.
   * @param attempts
   *   The number of re-send attempts allowed.
   * @param replyHandler
   *   An asynchronous reply handler. This will be invoked with the message reply,
   *   or with a TimeoutException if the response times out.
   * @return
   *   The called Connection instance.
   */
  public <T> Connection send(Integer message, long timeout, boolean retry, int attempts, AsyncResultHandler<Message<T>> replyHandler);

  /**
   * Sends a message with a default timeout.
   *
   * @param message
   *   The message.
   * @param replyHandler
   *   An asynchronous reply handler. This will be invoked with the message reply,
   *   or with a TimeoutException if the response times out.
   */
  public <T> Connection send(Long message, AsyncResultHandler<Message<T>> replyHandler);

  /**
   * Sends a message with a timeout.
   *
   * @param message
   *   The message.
   * @param timeout
   *   The message timeout.
   * @param replyHandler
   *   An asynchronous reply handler. This will be invoked with the message reply,
   *   or with a TimeoutException if the response times out.
   */
  public <T> Connection send(Long message, long timeout, AsyncResultHandler<Message<T>> replyHandler);

  /**
   * Sends a message with a timeout, attempting to re-send the message if a timeout occurs.
   *
   * @param message
   *   The message.
   * @param timeout
   *   The message timeout.
   * @param retry
   *   A boolean indicating whether to attempt to re-send messages.
   * @param attempts
   *   The number of re-send attempts allowed.
   * @param replyHandler
   *   An asynchronous reply handler. This will be invoked with the message reply,
   *   or with a TimeoutException if the response times out.
   * @return
   *   The called Connection instance.
   */
  public <T> Connection send(Long message, long timeout, boolean retry, AsyncResultHandler<Message<T>> replyHandler);

  /**
   * Sends a message with a timeout, attempting to re-send the message if a timeout occurs.
   *
   * @param message
   *   The message.
   * @param timeout
   *   The message timeout.
   * @param retry
   *   A boolean indicating whether to attempt to re-send messages.
   * @param attempts
   *   The number of re-send attempts allowed.
   * @param replyHandler
   *   An asynchronous reply handler. This will be invoked with the message reply,
   *   or with a TimeoutException if the response times out.
   * @return
   *   The called Connection instance.
   */
  public <T> Connection send(Long message, long timeout, boolean retry, int attempts, AsyncResultHandler<Message<T>> replyHandler);

  /**
   * Sends a message with a default timeout.
   *
   * @param message
   *   The message.
   * @param replyHandler
   *   An asynchronous reply handler. This will be invoked with the message reply,
   *   or with a TimeoutException if the response times out.
   */
  public <T> Connection send(Float message, AsyncResultHandler<Message<T>> replyHandler);

  /**
   * Sends a message with a timeout.
   *
   * @param message
   *   The message.
   * @param timeout
   *   The message timeout.
   * @param replyHandler
   *   An asynchronous reply handler. This will be invoked with the message reply,
   *   or with a TimeoutException if the response times out.
   */
  public <T> Connection send(Float message, long timeout, AsyncResultHandler<Message<T>> replyHandler);

  /**
   * Sends a message with a timeout, attempting to re-send the message if a timeout occurs.
   *
   * @param message
   *   The message.
   * @param timeout
   *   The message timeout.
   * @param retry
   *   A boolean indicating whether to attempt to re-send messages.
   * @param attempts
   *   The number of re-send attempts allowed.
   * @param replyHandler
   *   An asynchronous reply handler. This will be invoked with the message reply,
   *   or with a TimeoutException if the response times out.
   * @return
   *   The called Connection instance.
   */
  public <T> Connection send(Float message, long timeout, boolean retry, AsyncResultHandler<Message<T>> replyHandler);

  /**
   * Sends a message with a timeout, attempting to re-send the message if a timeout occurs.
   *
   * @param message
   *   The message.
   * @param timeout
   *   The message timeout.
   * @param retry
   *   A boolean indicating whether to attempt to re-send messages.
   * @param attempts
   *   The number of re-send attempts allowed.
   * @param replyHandler
   *   An asynchronous reply handler. This will be invoked with the message reply,
   *   or with a TimeoutException if the response times out.
   * @return
   *   The called Connection instance.
   */
  public <T> Connection send(Float message, long timeout, boolean retry, int attempts, AsyncResultHandler<Message<T>> replyHandler);

  /**
   * Sends a message with a default timeout.
   *
   * @param message
   *   The message.
   * @param replyHandler
   *   An asynchronous reply handler. This will be invoked with the message reply,
   *   or with a TimeoutException if the response times out.
   */
  public <T> Connection send(Double message, AsyncResultHandler<Message<T>> replyHandler);

  /**
   * Sends a message with a timeout.
   *
   * @param message
   *   The message.
   * @param timeout
   *   The message timeout.
   * @param replyHandler
   *   An asynchronous reply handler. This will be invoked with the message reply,
   *   or with a TimeoutException if the response times out.
   */
  public <T> Connection send(Double message, long timeout, AsyncResultHandler<Message<T>> replyHandler);

  /**
   * Sends a message with a timeout, attempting to re-send the message if a timeout occurs.
   *
   * @param message
   *   The message.
   * @param timeout
   *   The message timeout.
   * @param retry
   *   A boolean indicating whether to attempt to re-send messages.
   * @param attempts
   *   The number of re-send attempts allowed.
   * @param replyHandler
   *   An asynchronous reply handler. This will be invoked with the message reply,
   *   or with a TimeoutException if the response times out.
   * @return
   *   The called Connection instance.
   */
  public <T> Connection send(Double message, long timeout, boolean retry, AsyncResultHandler<Message<T>> replyHandler);

  /**
   * Sends a message with a timeout, attempting to re-send the message if a timeout occurs.
   *
   * @param message
   *   The message.
   * @param timeout
   *   The message timeout.
   * @param retry
   *   A boolean indicating whether to attempt to re-send messages.
   * @param attempts
   *   The number of re-send attempts allowed.
   * @param replyHandler
   *   An asynchronous reply handler. This will be invoked with the message reply,
   *   or with a TimeoutException if the response times out.
   * @return
   *   The called Connection instance.
   */
  public <T> Connection send(Double message, long timeout, boolean retry, int attempts, AsyncResultHandler<Message<T>> replyHandler);

  /**
   * Sends a message with a default timeout.
   *
   * @param message
   *   The message.
   * @param replyHandler
   *   An asynchronous reply handler. This will be invoked with the message reply,
   *   or with a TimeoutException if the response times out.
   */
  public <T> Connection send(Boolean message, AsyncResultHandler<Message<T>> replyHandler);

  /**
   * Sends a message with a timeout.
   *
   * @param message
   *   The message.
   * @param timeout
   *   The message timeout.
   * @param replyHandler
   *   An asynchronous reply handler. This will be invoked with the message reply,
   *   or with a TimeoutException if the response times out.
   */
  public <T> Connection send(Boolean message, long timeout, AsyncResultHandler<Message<T>> replyHandler);

  /**
   * Sends a message with a timeout, attempting to re-send the message if a timeout occurs.
   *
   * @param message
   *   The message.
   * @param timeout
   *   The message timeout.
   * @param retry
   *   A boolean indicating whether to attempt to re-send messages.
   * @param attempts
   *   The number of re-send attempts allowed.
   * @param replyHandler
   *   An asynchronous reply handler. This will be invoked with the message reply,
   *   or with a TimeoutException if the response times out.
   * @return
   *   The called Connection instance.
   */
  public <T> Connection send(Boolean message, long timeout, boolean retry, AsyncResultHandler<Message<T>> replyHandler);

  /**
   * Sends a message with a timeout, attempting to re-send the message if a timeout occurs.
   *
   * @param message
   *   The message.
   * @param timeout
   *   The message timeout.
   * @param retry
   *   A boolean indicating whether to attempt to re-send messages.
   * @param attempts
   *   The number of re-send attempts allowed.
   * @param replyHandler
   *   An asynchronous reply handler. This will be invoked with the message reply,
   *   or with a TimeoutException if the response times out.
   * @return
   *   The called Connection instance.
   */
  public <T> Connection send(Boolean message, long timeout, boolean retry, int attempts, AsyncResultHandler<Message<T>> replyHandler);

  /**
   * Sends a message with a default timeout.
   *
   * @param message
   *   The message.
   * @param replyHandler
   *   An asynchronous reply handler. This will be invoked with the message reply,
   *   or with a TimeoutException if the response times out.
   */
  public <T> Connection send(Short message, AsyncResultHandler<Message<T>> replyHandler);

  /**
   * Sends a message with a timeout.
   *
   * @param message
   *   The message.
   * @param timeout
   *   The message timeout.
   * @param replyHandler
   *   An asynchronous reply handler. This will be invoked with the message reply,
   *   or with a TimeoutException if the response times out.
   */
  public <T> Connection send(Short message, long timeout, AsyncResultHandler<Message<T>> replyHandler);

  /**
   * Sends a message with a timeout, attempting to re-send the message if a timeout occurs.
   *
   * @param message
   *   The message.
   * @param timeout
   *   The message timeout.
   * @param retry
   *   A boolean indicating whether to attempt to re-send messages.
   * @param attempts
   *   The number of re-send attempts allowed.
   * @param replyHandler
   *   An asynchronous reply handler. This will be invoked with the message reply,
   *   or with a TimeoutException if the response times out.
   * @return
   *   The called Connection instance.
   */
  public <T> Connection send(Short message, long timeout, boolean retry, AsyncResultHandler<Message<T>> replyHandler);

  /**
   * Sends a message with a timeout, attempting to re-send the message if a timeout occurs.
   *
   * @param message
   *   The message.
   * @param timeout
   *   The message timeout.
   * @param retry
   *   A boolean indicating whether to attempt to re-send messages.
   * @param attempts
   *   The number of re-send attempts allowed.
   * @param replyHandler
   *   An asynchronous reply handler. This will be invoked with the message reply,
   *   or with a TimeoutException if the response times out.
   * @return
   *   The called Connection instance.
   */
  public <T> Connection send(Short message, long timeout, boolean retry, int attempts, AsyncResultHandler<Message<T>> replyHandler);

  /**
   * Sends a message with a default timeout.
   *
   * @param message
   *   The message.
   * @param replyHandler
   *   An asynchronous reply handler. This will be invoked with the message reply,
   *   or with a TimeoutException if the response times out.
   */
  public <T> Connection send(Character message, AsyncResultHandler<Message<T>> replyHandler);

  /**
   * Sends a message with a timeout.
   *
   * @param message
   *   The message.
   * @param timeout
   *   The message timeout.
   * @param replyHandler
   *   An asynchronous reply handler. This will be invoked with the message reply,
   *   or with a TimeoutException if the response times out.
   */
  public <T> Connection send(Character message, long timeout, AsyncResultHandler<Message<T>> replyHandler);

  /**
   * Sends a message with a timeout, attempting to re-send the message if a timeout occurs.
   *
   * @param message
   *   The message.
   * @param timeout
   *   The message timeout.
   * @param retry
   *   A boolean indicating whether to attempt to re-send messages.
   * @param attempts
   *   The number of re-send attempts allowed.
   * @param replyHandler
   *   An asynchronous reply handler. This will be invoked with the message reply,
   *   or with a TimeoutException if the response times out.
   * @return
   *   The called Connection instance.
   */
  public <T> Connection send(Character message, long timeout, boolean retry, AsyncResultHandler<Message<T>> replyHandler);

  /**
   * Sends a message with a timeout, attempting to re-send the message if a timeout occurs.
   *
   * @param message
   *   The message.
   * @param timeout
   *   The message timeout.
   * @param retry
   *   A boolean indicating whether to attempt to re-send messages.
   * @param attempts
   *   The number of re-send attempts allowed.
   * @param replyHandler
   *   An asynchronous reply handler. This will be invoked with the message reply,
   *   or with a TimeoutException if the response times out.
   * @return
   *   The called Connection instance.
   */
  public <T> Connection send(Character message, long timeout, boolean retry, int attempts, AsyncResultHandler<Message<T>> replyHandler);

  /**
   * Sends a message with a default timeout.
   *
   * @param message
   *   The message.
   * @param replyHandler
   *   An asynchronous reply handler. This will be invoked with the message reply,
   *   or with a TimeoutException if the response times out.
   */
  public <T> Connection send(Byte message, AsyncResultHandler<Message<T>> replyHandler);

  /**
   * Sends a message with a timeout.
   *
   * @param message
   *   The message.
   * @param timeout
   *   The message timeout.
   * @param replyHandler
   *   An asynchronous reply handler. This will be invoked with the message reply,
   *   or with a TimeoutException if the response times out.
   */
  public <T> Connection send(Byte message, long timeout, AsyncResultHandler<Message<T>> replyHandler);

  /**
   * Sends a message with a timeout, attempting to re-send the message if a timeout occurs.
   *
   * @param message
   *   The message.
   * @param timeout
   *   The message timeout.
   * @param retry
   *   A boolean indicating whether to attempt to re-send messages.
   * @param attempts
   *   The number of re-send attempts allowed.
   * @param replyHandler
   *   An asynchronous reply handler. This will be invoked with the message reply,
   *   or with a TimeoutException if the response times out.
   * @return
   *   The called Connection instance.
   */
  public <T> Connection send(Byte message, long timeout, boolean retry, AsyncResultHandler<Message<T>> replyHandler);

  /**
   * Sends a message with a timeout, attempting to re-send the message if a timeout occurs.
   *
   * @param message
   *   The message.
   * @param timeout
   *   The message timeout.
   * @param retry
   *   A boolean indicating whether to attempt to re-send messages.
   * @param attempts
   *   The number of re-send attempts allowed.
   * @param replyHandler
   *   An asynchronous reply handler. This will be invoked with the message reply,
   *   or with a TimeoutException if the response times out.
   * @return
   *   The called Connection instance.
   */
  public <T> Connection send(Byte message, long timeout, boolean retry, int attempts, AsyncResultHandler<Message<T>> replyHandler);

}
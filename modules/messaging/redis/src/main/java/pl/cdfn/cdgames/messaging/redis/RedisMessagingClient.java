package pl.cdfn.cdgames.messaging.redis;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import pl.cdfn.cdgames.messaging.api.Message;
import pl.cdfn.cdgames.messaging.api.MessageListener;
import pl.cdfn.cdgames.messaging.api.MessagingChannel;
import pl.cdfn.cdgames.messaging.api.MessagingClient;

class RedisMessagingClient implements MessagingClient<RedisClient> {

  private final String identifier;
  private final RedisURI uri;

  RedisMessagingClient(String identifier, RedisURI uri) {
    this.identifier = identifier;
    this.uri = uri;
  }

  /**
   * Gets identifier of MessagingClient - e.g. one server would have own identifier so other
   * servers know who sent the message
   *
   * @return identifier
   */
  @Override
  public String getIdentifier() {
    return this.identifier;
  }

  /**
   *
   * @param messageClass message class
   * @param topic string used to identify messages
   * @param messageListener listener triggered when message is received
   * @param <U> message type
   * @return new messaging channel
   */
  @Override
  public <U extends Message> MessagingChannel<U> createMessagingChannel(
      Class<U> messageClass,
      String topic,
      MessageListener<U> messageListener
  ) {
    return new RedisMessagingChannel<>(messageClass, this, topic, messageListener);
  }


  /**
   * @return new <code>RedisClient</code> instance
   */
  @Override
  public RedisClient getUnderlyingClient() {
    return RedisClient.create(uri);
  }

  /**
   * @return URI for redis connection
   */
  public RedisURI getURI() {
    return this.uri;
  }
}

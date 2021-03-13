package pl.cdfn.cdgames.messaging.redis;

import io.lettuce.core.codec.ByteArrayCodec;
import io.lettuce.core.pubsub.StatefulRedisPubSubConnection;
import java.nio.charset.StandardCharsets;
import pl.cdfn.cdgames.messaging.api.Message;
import pl.cdfn.cdgames.messaging.api.MessageListener;
import pl.cdfn.cdgames.messaging.api.MessagingChannel;

class RedisMessagingChannel<T extends Message> extends
    MessagingChannel<T> {

  private final StatefulRedisPubSubConnection<byte[], byte[]> publisherConnection;
  private final StatefulRedisPubSubConnection<byte[], byte[]> subscriberConnection;
  private final MessageListener<T> listener;

  /**
   * @param messagingClient underlying redis messaging client
   * @param topic           topic to subscribe to
   */
  RedisMessagingChannel(Class<T> messageClass, RedisMessagingClient messagingClient, String topic,
      MessageListener<T> listener) {
    super(messageClass, topic);

    this.publisherConnection = messagingClient
        .getUnderlyingClient()
        .connectPubSub(ByteArrayCodec.INSTANCE, messagingClient.getURI());
    this.subscriberConnection = messagingClient
        .getUnderlyingClient()
        .connectPubSub(ByteArrayCodec.INSTANCE, messagingClient.getURI());

    this.listener = listener;
  }

  /**
   * Publishes serialized message to all subscribed clients
   *
   * @param message message to publish
   */
  @Override
  public void publish(Message message) {
    var topic = getTopic().getBytes(StandardCharsets.UTF_8);
    var byteMessage = message.serialize();
    publisherConnection.sync().publish(topic, byteMessage);
  }

  /**
   * Subscribes to specific topic and triggers {@link MessageListener} assigned to this channel when
   * message is received
   */
  @Override
  public void subscribe() {
    this.subscriberConnection.sync().subscribe(this.getTopic().getBytes(StandardCharsets.UTF_8));
    this.subscriberConnection.addListener(new RedisPubSubListenerImpl<>(this, listener));
  }

}

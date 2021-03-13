package pl.cdfn.cdgames.messaging.redis;

import io.lettuce.core.pubsub.RedisPubSubListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.cdfn.cdgames.messaging.api.Message;
import pl.cdfn.cdgames.messaging.api.MessageListener;
import pl.cdfn.cdgames.messaging.api.MessagingChannel;
import pl.cdfn.cdgames.messaging.api.util.reflection.InstanceUtil;

class RedisPubSubListenerImpl<T extends Message, L extends MessageListener<T>> implements
    RedisPubSubListener<byte[], byte[]> {

  private static final Logger LOGGER = LoggerFactory.getLogger(RedisPubSubListenerImpl.class);
  private final MessagingChannel<T> messagingChannel;
  private final L listener;

  RedisPubSubListenerImpl(MessagingChannel<T> messagingChannel, L listener) {
    this.listener = listener;
    this.messagingChannel = messagingChannel;
  }

  @Override
  public void message(byte[] channel, byte[] message) {
    LOGGER.debug("Message received on channel {}", new String(channel));
    T messageInstance = InstanceUtil.createInstance(messagingChannel.getMessageClass());
    messageInstance.deserialize(message);
    listener.onMessage(messageInstance);
  }

  @Override
  public void unsubscribed(byte[] channel, long count) {
    LOGGER.info("Unsubscribed from channel {}, {} subscribers left", new String(channel), count);

  }
  @Override
  public void subscribed(byte[] channel, long count) {
    LOGGER.info("Subscribed to channel {} with {} subscribers", new String(channel), count);
  }

  @Override
  public void message(byte[] pattern, byte[] channel, byte[] message) { }

  @Override
  public void psubscribed(byte[] pattern, long count) { }

  @Override
  public void punsubscribed(byte[] pattern, long count) { }
}

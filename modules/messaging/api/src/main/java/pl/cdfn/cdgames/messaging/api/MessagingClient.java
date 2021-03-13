package pl.cdfn.cdgames.messaging.api;

/**
 * @param <T> underlying client type
 */
public interface MessagingClient<T> {

  /**
   * Gets identifier of MessagingClient - e.g. one server would have own identifier so other
   * servers know who sent the message
   *
   * @return identifier
   */
  String getIdentifier();

  /**
   * Creates messaging channel with specified topic
   *
   * @param topic string used to identify messages
   * @return new messaging channel with specified topic
   */
  <U extends Message> MessagingChannel<U> createMessagingChannel(Class<U> messageClass, String topic, MessageListener<U> messageListener);

  /**
   * Gets client of specific implementation
   *
   * @return underlying impl-specific client
   */
  T getUnderlyingClient();
}

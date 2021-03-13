package pl.cdfn.cdgames.messaging.api;

public interface MessageListener<T extends Message> {

  /**
   * Triggers when new message is sent to a channel
   *
   * @param message received message
   */
  void onMessage(T message);
}

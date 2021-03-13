package pl.cdfn.cdgames.messaging.api;

interface Subscriber {

  /**
   * Subscribes to specific topic and triggers {@link MessageListener} assigned to this channel when
   * message is received
   */
  void subscribe();
}

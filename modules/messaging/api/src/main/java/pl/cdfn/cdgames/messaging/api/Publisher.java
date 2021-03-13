package pl.cdfn.cdgames.messaging.api;

interface Publisher {

  /**
   * Publishes message to all subscribed clients to specific topic
   *
   * @param message message to publish
   */
  void publish(Message message);
}

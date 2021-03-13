package pl.cdfn.cdgames.messaging.api;

public abstract class MessagingChannel<T extends Message> implements Subscriber, Publisher {

  private final Class<T> messageClass;
  private final String topic;

  public MessagingChannel(Class<T> messageClass, String topic) {
    this.messageClass = messageClass;
    this.topic = topic;
  }


  public String getTopic() {
    return this.topic;
  }

  public Class<T> getMessageClass() {
    return this.messageClass;
  }
}

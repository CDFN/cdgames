package pl.cdfn.cdgames.messaging.api;

public interface MessagingClientFactory {

  /**
   * @param identifier string used to identify client
   * @return new messaging client
   */
  MessagingClient<?> createClient(String identifier);

}

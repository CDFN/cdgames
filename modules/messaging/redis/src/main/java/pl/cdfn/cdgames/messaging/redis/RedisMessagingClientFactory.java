package pl.cdfn.cdgames.messaging.redis;

import io.lettuce.core.RedisURI;
import pl.cdfn.cdgames.messaging.api.MessagingClient;
import pl.cdfn.cdgames.messaging.api.MessagingClientFactory;

public class RedisMessagingClientFactory implements MessagingClientFactory {

  private final RedisURI uri;

  public RedisMessagingClientFactory(RedisURI uri) {
    this.uri = uri;
  }

  /**
   *
   * @param identifier string used to identify client
   * @return new redis messaging client
   */
  @Override
  public MessagingClient<?> createClient(String identifier) {
    return new RedisMessagingClient(identifier, uri);
  }
}

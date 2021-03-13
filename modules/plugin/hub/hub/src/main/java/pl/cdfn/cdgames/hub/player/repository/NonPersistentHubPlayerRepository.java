package pl.cdfn.cdgames.hub.player.repository;

import com.google.common.collect.Maps;
import com.google.inject.Singleton;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import org.spongepowered.api.Sponge;
import pl.cdfn.cdgames.hub.player.HubPlayer;

@Singleton
public class NonPersistentHubPlayerRepository implements HubPlayerRepository {

  private final Map<UUID, HubPlayer> playerMap = Maps.newHashMap();

  @Override
  public CompletableFuture<HubPlayer> getPlayer(UUID uuid) {
    if (!playerMap.containsKey(uuid)) {
      Sponge.getServer().getPlayer(uuid).ifPresentOrElse(serverPlayer -> {
        var player = new HubPlayer(serverPlayer);
        playerMap.put(uuid, player);
      }, () -> {
        throw new IllegalArgumentException("accessing hub player cache for offline player");
      });
    }

    return CompletableFuture.completedFuture(playerMap.get(uuid));
  }
}

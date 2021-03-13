package pl.cdfn.cdgames.hub.player.repository;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import pl.cdfn.cdgames.hub.player.HubPlayer;

public interface HubPlayerRepository {
  CompletableFuture<HubPlayer> getPlayer(UUID key);
  void cleanup(UUID key);
}

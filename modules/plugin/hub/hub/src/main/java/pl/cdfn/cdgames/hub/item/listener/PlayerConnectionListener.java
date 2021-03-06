package pl.cdfn.cdgames.hub.item.listener;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import org.apache.logging.log4j.Logger;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.network.ServerSideConnectionEvent;
import pl.cdfn.cdgames.hub.event.PlayerJoinSettingsEvent;
import pl.cdfn.cdgames.hub.player.repository.HubPlayerRepository;

public class PlayerConnectionListener {

  private final Logger logger;
  private final HubPlayerRepository hubPlayerRepository;

  @Inject
  public PlayerConnectionListener(Logger logger, @Named("nonpersistent") HubPlayerRepository hubPlayerRepository) {
    this.logger = logger;
    this.hubPlayerRepository = hubPlayerRepository;
  }

  @Listener
  public void onPlayerJoin(PlayerJoinSettingsEvent event) {
    hubPlayerRepository.getPlayer(event.player().uniqueId()).thenAccept(hubPlayer -> {
      var itemManager = hubPlayer.getItemManager();

      var inventory = event.player().inventory();
      var hotbar = inventory.hotbar();

      inventory.clear();
      hotbar.setSelectedSlotIndex(4);
      hotbar.set(5 - 1, itemManager.getGameSelector());
      hotbar.set(9 - 1, itemManager.getPlayerHider());
    });

  }

  @Listener
  public void onPlayerLeave(ServerSideConnectionEvent.Disconnect event) {
    hubPlayerRepository.cleanup(event.player().uniqueId());
  }

}

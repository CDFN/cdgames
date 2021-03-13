package pl.cdfn.cdgames.hub.item.listener;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import java.util.UUID;
import org.apache.logging.log4j.Logger;
import org.spongepowered.api.event.Listener;
import pl.cdfn.cdgames.hub.event.PlayerJoinSettingsEvent;
import pl.cdfn.cdgames.hub.player.repository.HubPlayerRepository;
import pl.cdfn.cdgames.hub.player.repository.NonPersistentHubPlayerRepository;

public class PlayerJoinListener {

  private final Logger logger;
  private final HubPlayerRepository hubPlayerRepository;

  @Inject
  public PlayerJoinListener(Logger logger, @Named("nonpersistent") HubPlayerRepository hubPlayerRepository) {
    this.logger = logger;
    this.hubPlayerRepository = hubPlayerRepository;
  }

  @Listener
  public void onPlayerJoin(PlayerJoinSettingsEvent event) {
    hubPlayerRepository.getPlayer(event.getPlayer().getUniqueId()).thenAccept(hubPlayer -> {
      var itemManager = hubPlayer.getItemManager();

      event.getPlayer().getInventory().clear();
      event.getPlayer().getInventory().getHotbar().setSelectedSlotIndex(4);

      event.getPlayer().getInventory().getHotbar().set(5 - 1, itemManager.getGameSelector());
      event.getPlayer().getInventory().getHotbar().set(9 - 1, itemManager.getPlayerHider());
    });

  }

}

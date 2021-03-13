package pl.cdfn.cdgames.hub.player;

import com.google.common.collect.ImmutableList;
import org.spongepowered.api.entity.living.player.server.ServerPlayer;
import pl.cdfn.cdgames.hub.item.manager.HubItemManager;
import pl.cdfn.cdgames.hub.item.stateful.IStatefulItem;
import pl.cdfn.cdgames.hub.item.stateful.impl.GameSelector;
import pl.cdfn.cdgames.hub.item.stateful.impl.PlayerHider;
import pl.cdfn.cdgames.hub.player.status.HiddenPlayersStatus;

public class HubPlayer {

  private final ImmutableList<IStatefulItem> statefulItems;
  private final ServerPlayer serverPlayer;
  private final HubItemManager itemManager;
  private HiddenPlayersStatus hiddenPlayersStatus;

  public HubPlayer(ServerPlayer serverPlayer) {
    this.serverPlayer = serverPlayer;

    this.itemManager = new HubItemManager(this);
    this.hiddenPlayersStatus = HiddenPlayersStatus.ALL;
    this.statefulItems = ImmutableList.of(
        new PlayerHider(this),
        new GameSelector(this)
    );
  }

  public ServerPlayer getServerPlayer() {
    return serverPlayer;
  }

  public HubItemManager getItemManager() {
    return itemManager;
  }

  public HiddenPlayersStatus getHiddenPlayersStatus() {
    return this.hiddenPlayersStatus;
  }

  public void setHiddenPlayersStatus(HiddenPlayersStatus newStatus) {
    this.hiddenPlayersStatus = newStatus;
  }

  public ImmutableList<IStatefulItem> getStatefulItems() {
    return this.statefulItems;
  }
}

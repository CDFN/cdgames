package pl.cdfn.cdgames.hub.item.stateful;

import org.spongepowered.api.item.ItemType;
import pl.cdfn.cdgames.hub.player.HubPlayer;

public abstract class StatefulItem implements IStatefulItem{

  private final HubPlayer player;
  private final ItemType itemType;

  public StatefulItem(HubPlayer player, ItemType itemType) {

    this.player = player;
    this.itemType = itemType;
  }

  public HubPlayer getPlayer() {
    return player;
  }

  @Override
  public ItemType getItemType() {
    return this.itemType;
  }
}

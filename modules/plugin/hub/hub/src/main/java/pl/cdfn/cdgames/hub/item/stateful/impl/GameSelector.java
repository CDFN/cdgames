package pl.cdfn.cdgames.hub.item.stateful.impl;

import net.kyori.adventure.text.Component;
import org.spongepowered.api.data.Keys;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.ContainerTypes;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.ItemStackSnapshot;
import org.spongepowered.api.item.inventory.type.ViewableInventory;
import pl.cdfn.cdgames.hub.item.stateful.StatefulItem;
import pl.cdfn.cdgames.hub.player.HubPlayer;

public class GameSelector extends StatefulItem {

  private static final ItemStackSnapshot GUI_EMPTY_SPACE = ItemStack
      .builder()
      .itemType(ItemTypes.GRAY_STAINED_GLASS_PANE)
      .add(Keys.CUSTOM_NAME, Component.empty())
      .build()
      .createSnapshot();

  public GameSelector(HubPlayer player) {
    super(player, ItemTypes.COMPASS.get());
  }

  @Override
  public ItemStack onUse(ItemStackSnapshot itemStack) {
    var menu = ViewableInventory
        .builder()
        .type(ContainerTypes.GENERIC_9X6)
        .fillDummy()
        .dummySlots(54, 0)
        .item(GUI_EMPTY_SPACE)
        .completeStructure()
        .build()
        .asMenu()
        .setReadOnly(true);

    menu.open(this.getPlayer().getServerPlayer());
    return itemStack.createStack();
  }
}

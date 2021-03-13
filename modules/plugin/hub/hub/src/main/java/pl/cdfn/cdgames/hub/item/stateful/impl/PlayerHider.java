package pl.cdfn.cdgames.hub.item.stateful.impl;

import java.util.List;
import org.spongepowered.api.data.Keys;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.enchantment.Enchantment;
import org.spongepowered.api.item.enchantment.EnchantmentTypes;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.ItemStackSnapshot;
import pl.cdfn.cdgames.hub.item.stateful.StatefulItem;
import pl.cdfn.cdgames.hub.player.HubPlayer;
import pl.cdfn.cdgames.hub.player.status.HiddenPlayersStatus;

public class PlayerHider extends StatefulItem {

  public PlayerHider(HubPlayer player) {
    super(player, ItemTypes.REDSTONE.get());
  }

  @Override
  public ItemStack onUse(ItemStackSnapshot itemStack) {
    var hiddenPlayersStatus = HiddenPlayersStatus.next(this.getPlayer().getHiddenPlayersStatus());
    this.getPlayer().setHiddenPlayersStatus(hiddenPlayersStatus);
    if (hiddenPlayersStatus.isGlowing()) {
      return ItemStack
          .builder()
          .fromSnapshot(itemStack)
          .add(Keys.APPLIED_ENCHANTMENTS, List.of(Enchantment.of(EnchantmentTypes.UNBREAKING, 10)))
          .build();
    }
    return ItemStack
        .builder()
        .fromSnapshot(itemStack)
        .add(Keys.APPLIED_ENCHANTMENTS, List.of())
        .build();
  }
}

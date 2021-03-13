package pl.cdfn.cdgames.hub.item.stateful;

import org.spongepowered.api.entity.living.player.server.ServerPlayer;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.ItemStackSnapshot;

public interface IStatefulItem {
    ItemStack onUse(ItemStackSnapshot itemStack);
    ItemType getItemType();
}

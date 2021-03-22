package pl.cdfn.cdgames.hub.item.manager;

import java.util.List;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.translation.GlobalTranslator;
import org.spongepowered.api.data.Keys;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.enchantment.Enchantment;
import org.spongepowered.api.item.enchantment.EnchantmentTypes;
import org.spongepowered.api.item.inventory.ItemStack;
import pl.cdfn.cdgames.hub.player.HubPlayer;

public class HubItemManager {
  private final HubPlayer hubPlayer;

  public HubItemManager(HubPlayer hubPlayer) {
    this.hubPlayer = hubPlayer;
  }

  public ItemStack getGameSelector() {
    var itemName = Component
        .translatable("hub.item.selector.name")
        .color(TextColor.color(0xFFDE2E))
        .decoration(TextDecoration.ITALIC, false);

    var translatedItemName = GlobalTranslator.render(itemName, hubPlayer.getServerPlayer().locale());

    return ItemStack.builder()
        .itemType(ItemTypes.COMPASS)
        .add(Keys.CUSTOM_NAME, translatedItemName)
        // random enchant so item is shining
        .add(Keys.APPLIED_ENCHANTMENTS, List.of(Enchantment.of(EnchantmentTypes.UNBREAKING, 10)))
        .add(Keys.HIDE_ENCHANTMENTS, true)
        .build();
  }

  public ItemStack getPlayerHider() {
    var itemName = Component
        .translatable("hub.item.hide_all.name")
        .color(TextColor.color(0xFFDE2E))
        .decoration(TextDecoration.ITALIC, false);

    var translatedItemName = GlobalTranslator.render(itemName, hubPlayer.getServerPlayer().locale());

    return ItemStack.builder()
        .itemType(ItemTypes.REDSTONE)
        .add(Keys.CUSTOM_NAME, translatedItemName)
        // random enchant so item is shining
        .add(Keys.APPLIED_ENCHANTMENTS, List.of(Enchantment.of(EnchantmentTypes.UNBREAKING, 10)))
        .add(Keys.HIDE_ENCHANTMENTS, true)
        .build();
  }

}

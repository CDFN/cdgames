package pl.cdfn.cdgames.hub.item.listener;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import org.spongepowered.api.data.type.HandTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.EventContextKeys;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.filter.cause.First;
import org.spongepowered.api.event.item.inventory.InteractItemEvent;
import pl.cdfn.cdgames.hub.player.repository.HubPlayerRepository;

public class PlayerItemListener {

  private final HubPlayerRepository hubPlayerRepository;

  @Inject
  public PlayerItemListener(@Named("nonpersistent") HubPlayerRepository hubPlayerRepository) {
    this.hubPlayerRepository = hubPlayerRepository;
  }

  @Listener
  public void onUse(InteractItemEvent.Secondary event, @First Player player) {
    var itemType = event.getItemStack().getType();

    hubPlayerRepository.getPlayer(player.getUniqueId()).thenAccept(hubPlayer -> {
      var optionalStatefulItem = hubPlayer.getStatefulItems()
          .stream()
          .filter(statefulItem -> statefulItem.getItemType().equals(itemType))
          .findFirst();

      if (optionalStatefulItem.isEmpty()) {
        return;
      }

      var statefulItem = optionalStatefulItem.get();
      var useResult = statefulItem.onUse(event.getItemStack());

      var optionalHandType = event.getCause().getContext().get(EventContextKeys.USED_HAND);
      if (optionalHandType.isEmpty()) {
        return;
      }

      var handType = optionalHandType.get();
      var inventory = player.getInventory();

      if (HandTypes.MAIN_HAND.get().equals(handType)) {
        var selectedSlot = inventory.getHotbar().getSelectedSlotIndex();
        inventory.set(selectedSlot, useResult);
        return;
      }
      inventory.getOffhand().set(useResult);

    });
  }

}

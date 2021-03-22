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
    var itemType = event.itemStack().type();

    hubPlayerRepository.getPlayer(player.uniqueId()).thenAccept(hubPlayer -> {
      var optionalStatefulItem = hubPlayer.getStatefulItems()
          .stream()
          .filter(statefulItem -> statefulItem.getItemType().equals(itemType))
          .findFirst();

      if (optionalStatefulItem.isEmpty()) {
        return;
      }

      var statefulItem = optionalStatefulItem.get();
      var useResult = statefulItem.onUse(event.itemStack());

      var optionalHandType = event.cause().context().get(EventContextKeys.USED_HAND);
      if (optionalHandType.isEmpty()) {
        return;
      }

      var handType = optionalHandType.get();
      var inventory = player.inventory();

      if (HandTypes.MAIN_HAND.get().equals(handType)) {
        var selectedSlot = inventory.hotbar().selectedSlotIndex();
        inventory.set(selectedSlot, useResult);
        return;
      }
      inventory.offhand().set(useResult);

    });
  }

}

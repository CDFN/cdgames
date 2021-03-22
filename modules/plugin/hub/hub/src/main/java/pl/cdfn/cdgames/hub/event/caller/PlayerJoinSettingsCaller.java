package pl.cdfn.cdgames.hub.event.caller;

import com.google.common.collect.Sets;
import com.google.inject.Inject;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import org.apache.logging.log4j.Logger;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.server.ServerPlayer;
import org.spongepowered.api.event.Cause;
import org.spongepowered.api.event.EventContext;
import org.spongepowered.api.event.EventContextKeys;
import org.spongepowered.api.event.EventManager;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.entity.living.player.PlayerChangeClientSettingsEvent;
import org.spongepowered.api.event.network.ServerSideConnectionEvent;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.util.Ticks;
import org.spongepowered.plugin.PluginContainer;
import pl.cdfn.cdgames.hub.event.PlayerJoinSettingsEvent;

public class PlayerJoinSettingsCaller {

  private final Collection<UUID> waitingList = Sets.newHashSet();
  private final PluginContainer pluginContainer;
  private final EventManager eventManager;
  private final Logger logger;

  @Inject
  public PlayerJoinSettingsCaller(PluginContainer pluginContainer, EventManager eventManager,
      Logger logger) {
    this.pluginContainer = pluginContainer;
    this.eventManager = eventManager;
    this.logger = logger;
  }

  @Listener
  public void onJoin(ServerSideConnectionEvent.Join event) {
    waitingList.add(event.player().uniqueId());
  }

  @Listener
  public void onQuit(ServerSideConnectionEvent.Disconnect event) {
    waitingList.remove(event.player().uniqueId());
  }

  @Listener
  public void onSettingsPacket(PlayerChangeClientSettingsEvent event) {
    var player = event.player();
    if (!waitingList.contains(player.uniqueId())) {
      return;
    }

    waitingList.remove(player.uniqueId());
    call(player);
  }

  private void call(ServerPlayer player) {
    var eventContext = EventContext.of(Map.of(EventContextKeys.PLUGIN, pluginContainer));
    var cause = Cause.of(eventContext, pluginContainer);
    var postTask = Task.builder()
        .plugin(pluginContainer)
        .execute(() -> this.eventManager.post(new PlayerJoinSettingsEvent(player, cause)))
        // after one tick so player object stores updated locale
        .delay(Ticks.of(1))
        .build();

    Sponge.server().scheduler().submit(postTask);
  }
}

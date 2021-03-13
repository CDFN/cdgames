package pl.cdfn.cdgames.hub.event;

import org.spongepowered.api.entity.living.player.server.ServerPlayer;
import org.spongepowered.api.event.Cause;
import org.spongepowered.api.event.impl.AbstractEvent;

/**
 * Triggered after player joins the server ({@link org.spongepowered.api.event.network.ServerSideConnectionEvent.Join})
 * and sends it's settings ({@link org.spongepowered.api.event.entity.living.player.PlayerChangeClientSettingsEvent})
 */
public class PlayerJoinSettingsEvent extends AbstractEvent {

  private final ServerPlayer player;
  private final Cause cause;

  public PlayerJoinSettingsEvent(ServerPlayer player, Cause cause) {
    this.player = player;
    this.cause = cause;
  }

  @Override
  public Cause getCause() {
    return this.cause;
  }

  public ServerPlayer getPlayer() {
    return player;
  }
}

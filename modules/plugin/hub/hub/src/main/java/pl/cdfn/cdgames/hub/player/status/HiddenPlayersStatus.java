package pl.cdfn.cdgames.hub.player.status;

/**
 * Describes what players should we show, ALL means we should show all players etc.
 */
public enum HiddenPlayersStatus {
  ALL(true),
  FRIENDS(false),
  NONE(false);

  private final boolean glow;

  HiddenPlayersStatus(boolean glow) {
    this.glow = glow;
  }

  public static HiddenPlayersStatus next(HiddenPlayersStatus current) {
    return switch (current) {
      case ALL -> FRIENDS;
      case FRIENDS -> NONE;
      case NONE -> ALL;
    };
  }

  public boolean isGlowing() {
    return glow;
  }
}

package pl.cdfn.cdgames.hub;

import com.google.inject.Inject;
import com.google.inject.Injector;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.translation.GlobalTranslator;
import net.kyori.adventure.translation.TranslationRegistry;
import net.kyori.adventure.util.UTF8ResourceBundleControl;
import org.apache.logging.log4j.Logger;
import org.spongepowered.api.event.EventManager;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.lifecycle.LoadedGameEvent;
import org.spongepowered.api.util.locale.Locales;
import org.spongepowered.plugin.PluginContainer;
import org.spongepowered.plugin.jvm.Plugin;
import pl.cdfn.cdgames.hub.event.caller.PlayerJoinSettingsCaller;
import pl.cdfn.cdgames.hub.item.listener.PlayerConnectionListener;
import pl.cdfn.cdgames.hub.item.listener.PlayerItemListener;
import pl.cdfn.cdgames.hub.player.repository.module.HubPlayerRepositoryModule;

@Plugin("cdgames_hub")
public class HubPlugin {

  private static final Locale[] SUPPORTED_LOCALES = {
      Locales.EN_US,
      Locales.PL_PL
  };

  @Inject
  private Logger logger;

  @Inject
  private PluginContainer pluginContainer;

  @Inject
  private EventManager eventManager;

  @Inject
  private Injector injector;

  @Listener
  public void onServerStart(LoadedGameEvent event) {
    var childInjector = injector.createChildInjector(new HubPlayerRepositoryModule());

    var registry = TranslationRegistry.create(Key.key("hub", "hub_translation_registry"));
    GlobalTranslator.get().addSource(registry);
    for (var locale : SUPPORTED_LOCALES) {
      var bundle = PropertyResourceBundle.getBundle("hub", locale, UTF8ResourceBundleControl.get());
      logger.info("Registering locale {}", locale);
      registry.registerAll(locale, bundle, true);
    }

    // listener calling PlayerJoinSettingsEvent
    var playerJoinSettingsCaller = childInjector.getInstance(PlayerJoinSettingsCaller.class);
    eventManager.registerListeners(pluginContainer, playerJoinSettingsCaller);

    var itemUseListener = childInjector.getInstance(PlayerItemListener.class);
    eventManager.registerListeners(pluginContainer, itemUseListener);

    var serverSideJoinListener = childInjector.getInstance(PlayerConnectionListener.class);
    eventManager.registerListeners(pluginContainer, serverSideJoinListener);
  }
}

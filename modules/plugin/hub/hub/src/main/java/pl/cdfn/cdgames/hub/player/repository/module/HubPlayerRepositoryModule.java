package pl.cdfn.cdgames.hub.player.repository.module;

import com.google.inject.AbstractModule;
import com.google.inject.Key;
import com.google.inject.name.Names;
import java.util.UUID;
import pl.cdfn.cdgames.hub.player.repository.HubPlayerRepository;
import pl.cdfn.cdgames.hub.player.repository.NonPersistentHubPlayerRepository;

public class HubPlayerRepositoryModule extends AbstractModule {

  @Override
  protected void configure() {
    bind(HubPlayerRepository.class)
        .annotatedWith(Names.named("nonpersistent"))
        .to(NonPersistentHubPlayerRepository.class);
  }
}

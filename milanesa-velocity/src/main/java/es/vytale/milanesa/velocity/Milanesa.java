package es.vytale.milanesa.velocity;

import com.google.inject.Inject;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.plugin.Plugin;
import org.slf4j.Logger;

@Plugin(
        id = "milanesa-velocity",
        name = "Milanesa",
        version = "1.0-SNAPSHOT",
        description = "Núcleo principal del servidor, versión para Velocity",
        url = "https://vytale.es",
        authors = {"gatogamer"}
)
public class Milanesa {
    @Inject
    private Logger logger;

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
    }
}
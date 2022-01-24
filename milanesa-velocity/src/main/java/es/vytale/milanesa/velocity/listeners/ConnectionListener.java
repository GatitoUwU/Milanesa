package es.vytale.milanesa.velocity.listeners;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.DisconnectEvent;
import com.velocitypowered.api.event.connection.PostLoginEvent;
import com.velocitypowered.api.event.connection.PreLoginEvent;
import es.vytale.milanesa.common.executor.NekoExecutor;
import es.vytale.milanesa.common.proxy.ProxyManager;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

/**
 * This code has been created by
 * gatogamer#6666 A.K.A. gatogamer.
 * If you want to use my code, please
 * don't remove this messages and
 * give me the credits. Arigato! n.n
 */
@RequiredArgsConstructor
public class ConnectionListener {

    private final NekoExecutor nekoExecutor;
    private final ProxyManager proxyManager;

    @Subscribe
    public void onConnection(PreLoginEvent preLoginEvent) {
        if (proxyManager.isConnected(preLoginEvent.getUsername())) {
            preLoginEvent.setResult(PreLoginEvent.PreLoginComponentResult.denied(Component.text("Esta cuenta ya está dentro del servidor...").color(NamedTextColor.RED)));
            return;
        }
    }

    @Subscribe
    public void onPostConnection(PostLoginEvent event) {
        nekoExecutor.submit(() -> proxyManager.addPlayer(proxyManager.getSelf(), event.getPlayer().getUsername(), event.getPlayer().getUniqueId()));
    }

    @Subscribe
    public void onDisconnection(DisconnectEvent event) {
        nekoExecutor.submit(() -> proxyManager.removePlayer(proxyManager.getSelf(), event.getPlayer().getUsername(), event.getPlayer().getUniqueId()));
    }
}
package es.vytale.milanesa.velocity.listeners;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.DisconnectEvent;
import com.velocitypowered.api.event.connection.PostLoginEvent;
import com.velocitypowered.api.event.connection.PreLoginEvent;
import com.velocitypowered.api.event.player.PlayerChooseInitialServerEvent;
import com.velocitypowered.api.event.player.ServerPreConnectEvent;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.server.RegisteredServer;
import es.vytale.milanesa.velocity.Milanesa;
import es.vytale.milanesa.velocity.balancer.BalancerServers;
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

    private final Milanesa milanesa;

    @Subscribe
    public void onConnection(PreLoginEvent event) {
        if (milanesa.getProxyManager().isConnected(event.getUsername())) {
            event.setResult(PreLoginEvent.PreLoginComponentResult.denied(Component.text(
                    "Esta cuenta ya está dentro del servidor..."
            ).color(NamedTextColor.RED)));
        }
    }

    @Subscribe
    public void onAskForServer(PlayerChooseInitialServerEvent event) {
        Player player = event.getPlayer();

        try {
            RegisteredServer registeredServer = milanesa.getBalancerManager().getDefaultServer();
            if (registeredServer == null) {
                event.setInitialServer(milanesa.getProxyServer().getServer("limbo").orElse(null));
            } else {
                event.setInitialServer(registeredServer);
            }
        } catch (Exception e) {
            player.disconnect(Component.text(
                    "Error al procesar tu conexión, sí esto sigue ocurriendo, contáctanos en Discord..."
            ).color(NamedTextColor.RED));
        }
    }

    @Subscribe
    public void onServerConnection(ServerPreConnectEvent event) {
        Player player = event.getPlayer();

        RegisteredServer actualServer = event.getOriginalServer();
        RegisteredServer newServer = event.getResult().getServer().orElse(null);

        String actualServerName = actualServer.getServerInfo().getName();
        BalancerServers balancerServers = milanesa.getBalancerManager().getServers().get(actualServerName);

        if (newServer != null && balancerServers.getServers().contains(newServer)) {
            return; // Allow players to move between different lobbies.
        }

        try {
            RegisteredServer registeredServer = milanesa.getBalancerManager().getDefaultServer();
            if (registeredServer == null) {
                RegisteredServer limbo = milanesa.getProxyServer().getServer("limbo").orElse(null);
                if (limbo != null) {
                    event.setResult(ServerPreConnectEvent.ServerResult.allowed(limbo));
                } else {
                    event.setResult(ServerPreConnectEvent.ServerResult.denied());
                }
            } else {
                event.setResult(ServerPreConnectEvent.ServerResult.allowed(registeredServer));
            }
        } catch (Exception e) {
            player.disconnect(Component.text(
                    "Error al procesar tu conexión, sí esto sigue ocurriendo, contáctanos en Discord..."
            ).color(NamedTextColor.RED));
        }
    }

    @Subscribe
    public void onPostConnection(PostLoginEvent event) {
        Player player = event.getPlayer();
        milanesa.getNekoExecutor().submit(() ->
                milanesa.getProxyManager().addPlayer(
                        milanesa.getProxyManager().getSelf(), player.getUsername(), player.getUniqueId()
                )
        );
    }

    @Subscribe
    public void onDisconnection(DisconnectEvent event) {
        Player player = event.getPlayer();
        milanesa.getNekoExecutor().submit(() ->
                milanesa.getProxyManager().removePlayer(
                        milanesa.getProxyManager().getSelf(), player.getUsername(), player.getUniqueId()
                )
        );
    }
}
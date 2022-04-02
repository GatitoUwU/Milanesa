package es.vytale.milanesa.velocity.listeners;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.player.KickedFromServerEvent;
import com.velocitypowered.api.event.player.ServerPostConnectEvent;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.server.RegisteredServer;
import es.vytale.milanesa.velocity.Milanesa;
import lombok.RequiredArgsConstructor;

/**
 * This code has been created by
 * gatogamer#6666 A.K.A. gatogamer.
 * If you want to use my code, please
 * don't remove this messages and
 * give me the credits. Arigato! n.n
 */
@SuppressWarnings("all")
@RequiredArgsConstructor
public class ServerRouterListener {
    private final Milanesa milanesa;

    @Subscribe
    public void onConnect(ServerPostConnectEvent event) {
        Player player = event.getPlayer();
        player.getCurrentServer().ifPresent(serverConnection -> {
            if (serverConnection.getServerInfo().getName().equalsIgnoreCase("limbo")) {
                milanesa.getLimboQueueProcessor().getQueue().add(player);
            }
        });
    }

    @Subscribe
    public void onKick(KickedFromServerEvent event) {
        Player player = event.getPlayer();
        RegisteredServer rs = milanesa.getProxyServer().getServer("limbo").orElse(null);
        event.setResult(KickedFromServerEvent.RedirectPlayer.create(rs));
    }
}
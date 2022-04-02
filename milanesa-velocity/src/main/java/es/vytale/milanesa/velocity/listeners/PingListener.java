package es.vytale.milanesa.velocity.listeners;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyPingEvent;
import es.vytale.milanesa.common.proxy.ProxyManager;
import lombok.RequiredArgsConstructor;

/**
 * This code has been created by
 * gatogamer#6666 A.K.A. gatogamer.
 * If you want to use my code, please
 * don't remove this messages and
 * give me the credits. Arigato! n.n
 */
@RequiredArgsConstructor
public class PingListener {
    private final ProxyManager proxyManager;

    @Subscribe
    public void onPing(ProxyPingEvent event) {
        event.setPing(event.getPing().asBuilder().onlinePlayers(proxyManager.getConnected()).build());
    }
}
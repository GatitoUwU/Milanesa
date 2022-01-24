package es.vytale.milanesa.common.proxy.channels;

import com.google.gson.Gson;
import es.vytale.milanesa.common.proxy.PlayerUpdateData;
import es.vytale.milanesa.common.proxy.PlayerUpdateType;
import es.vytale.milanesa.common.proxy.ProxyManager;
import es.vytale.milanesa.common.redis.data.MilanesaChannel;
import es.vytale.milanesa.common.redis.data.MilanesaMessage;

import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This code has been created by
 * gatogamer#6666 A.K.A. gatogamer.
 * If you want to use my code, please
 * don't remove this messages and
 * give me the credits. Arigato! n.n
 */
public class ProxyPlayerUpdateChannel extends MilanesaChannel {
    private final ProxyManager proxyManager;
    private final Gson gson;

    public ProxyPlayerUpdateChannel(ProxyManager proxyManager) {
        super("proxy-player-update");
        this.proxyManager = proxyManager;
        gson = new Gson();
    }

    @Override
    public void handle(MilanesaMessage milanesaMessage) {
        System.out.println( "Handling proxy-player-update for request: " + milanesaMessage.getData());

        PlayerUpdateData pud = gson.fromJson(milanesaMessage.getData(), PlayerUpdateData.class);
        PlayerUpdateType put = pud.getPlayerUpdateType();
        Map<String, UUID> players = proxyManager.getOrCreate(pud.getProxy()).getPlayers();
        if (put.equals(PlayerUpdateType.ADD)) {
            players.put(pud.getName(), pud.getUuid());
        } else {
            players.remove(pud.getName());
        }
    }
}
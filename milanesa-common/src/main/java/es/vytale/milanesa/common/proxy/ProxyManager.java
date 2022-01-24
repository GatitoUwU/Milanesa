package es.vytale.milanesa.common.proxy;

import com.google.gson.Gson;
import es.vytale.milanesa.common.executor.NekoExecutor;
import es.vytale.milanesa.common.objects.CachedManager;
import es.vytale.milanesa.common.proxy.channels.ProxyCommunicationChannel;
import es.vytale.milanesa.common.proxy.channels.ProxyPlayerUpdateChannel;
import es.vytale.milanesa.common.proxy.channels.ProxyUpdateReplyChannel;
import es.vytale.milanesa.common.redis.MilanesaMessageHandler;
import lombok.Getter;

import javax.annotation.Nullable;
import java.util.UUID;

/**
 * This code has been created by
 * gatogamer#6666 A.K.A. gatogamer.
 * If you want to use my code, please
 * don't remove this messages and
 * give me the credits. Arigato! n.n
 */
@Getter
public class ProxyManager extends CachedManager<String, Proxy> {

    private final Gson gson;
    private final String actualProxy;
    private final NekoExecutor nekoExecutor;
    private final MilanesaMessageHandler milanesaMessageHandler;

    public ProxyManager(@Nullable String actualProxy, NekoExecutor nekoExecutor, MilanesaMessageHandler milanesaMessageHandler) {
        this.actualProxy = actualProxy;
        this.nekoExecutor = nekoExecutor;
        this.milanesaMessageHandler = milanesaMessageHandler;

        gson = new Gson();

        milanesaMessageHandler.registerChannel(new ProxyPlayerUpdateChannel(this));
        milanesaMessageHandler.registerChannel(new ProxyCommunicationChannel(this));

        if (actualProxy != null) {
            getValues().put(actualProxy, new Proxy(actualProxy));
            milanesaMessageHandler.registerChannel(
                    new ProxyUpdateReplyChannel(this, nekoExecutor, milanesaMessageHandler)
            );
            milanesaMessageHandler.sendMessage("proxy-update-request", actualProxy);
        } else {
            milanesaMessageHandler.sendMessage("proxy-update-request", "spigot-milanesa-instance");
        }
    }

    public void addPlayer(Proxy proxy, String name, UUID uuid) {
        String update = gson.toJson(new PlayerUpdateData(name, uuid, proxy.getName(), PlayerUpdateType.ADD));
        milanesaMessageHandler.sendMessage("proxy-player-update", update);
        proxy.getPlayers().put(name, uuid);
    }

    public void removePlayer(Proxy proxy, String name, UUID uuid) {
        String update = gson.toJson(new PlayerUpdateData(name, uuid, proxy.getName(), PlayerUpdateType.REMOVE));
        milanesaMessageHandler.sendMessage("proxy-player-update", update);
        proxy.getPlayers().remove(name);
    }

    public boolean isConnected(String name) {
        return getValues().values().stream().anyMatch(proxy -> proxy.isConnected(name));
    }

    public int getConnected() {
        return getAll().stream().mapToInt(Proxy::getOnline).sum();
    }

    public Proxy getSelf() {
        return getValues().get(actualProxy);
    }

    @Override
    public Proxy getOrCreate(String s) {
        return getValues().computeIfAbsent(s, ignored -> new Proxy(s));
    }
}
package es.vytale.milanesa.common.proxy;

import lombok.Data;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * This code has been created by
 * gatogamer#6666 A.K.A. gatogamer.
 * If you want to use my code, please
 * don't remove this messages and
 * give me the credits. Arigato! n.n
 */
@Data
public class Proxy {
    private final String name;
    private Map<String, UUID> players;

    public Proxy(String name) {
        this.name = name;
        players = new ConcurrentHashMap<>();
    }

    public int getOnline() {
        return players.size();
    }

    public boolean isConnected(String name) {
        return players.containsKey(name);
    }

    public boolean isConnected(UUID uuid) {
        return players.containsValue(uuid);
    }

    public void loadFromProxyData(ProxyData proxyData) {
        Map<String, UUID> players =  new ConcurrentHashMap<>();

        proxyData.getPlayers().entrySet().forEach(json ->
                players.put(json.getKey(), UUID.fromString(json.getValue().getAsString()))
        );

        setPlayers(players);
    }
}
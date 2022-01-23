package es.vytale.milanesa.common.proxy;

import lombok.Getter;

import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * This code has been created by
 * gatogamer#6666 A.K.A. gatogamer.
 * If you want to use my code, please
 * don't remove this messages and
 * give me the credits. Arigato! n.n
 */
@Getter
public class Proxy {
    private final String name;
    private final Set<UUID> players;

    public Proxy(String name) {
        this.name = name;
        players = ConcurrentHashMap.newKeySet();
    }

    public int getOnline() {
        return players.size();
    }

    public boolean isConnected(UUID uuid) {
        return players.contains(uuid);
    }
}
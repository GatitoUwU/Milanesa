package es.vytale.milanesa.common.proxy;

import com.google.gson.JsonObject;
import lombok.Data;

/**
 * This code has been created by
 * gatogamer#6666 A.K.A. gatogamer.
 * If you want to use my code, please
 * don't remove this messages and
 * give me the credits. Arigato! n.n
 */
@Data
public class ProxyData {
    private final String name;
    private final JsonObject players;

    public static ProxyData fromProxy(Proxy proxy) {
        JsonObject players = new JsonObject();
        proxy.getPlayers().forEach((s, uuid) -> players.addProperty(s, uuid.toString()));
        return new ProxyData(proxy.getName(), players);
    }
}
package es.vytale.milanesa.common.friends;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import es.vytale.milanesa.common.gson.ConcurrentHashMapTypeAdapter;
import lombok.Data;

import java.lang.reflect.Type;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * This code has been created by
 * gatogamer#6666 A.K.A. gatogamer.
 * If you want to use my code, please
 * don't remove this messages and
 * give me the credits. Arigato! n.n
 */
@Data
public class FriendProfile {
    private final UUID holder;
    private final ConcurrentMap<String, UUID> following;
    private final ConcurrentMap<String, UUID> followers;

    private static final Gson gson;
    static {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Type type = new TypeToken<ConcurrentHashMap<String, UUID>>() {}.getType();
        gsonBuilder.registerTypeAdapter(type, new ConcurrentHashMapTypeAdapter<String, UUID>());
        gson = gsonBuilder.create();
    }

    public static Gson gson() {
        return gson;
    }
}
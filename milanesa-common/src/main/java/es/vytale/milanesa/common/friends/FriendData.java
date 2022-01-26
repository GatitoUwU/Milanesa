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
public class FriendData {
    private final UUID holder;
    private final ConcurrentMap<UUID, String> following;
    private final ConcurrentMap<UUID, String> followers;

    public boolean isFollowing(UUID uuid) {
        return following.containsKey(uuid);
    }

    public boolean isFollower(UUID uuid) {
        return followers.containsKey(uuid);
    }

    private static final Gson gson;
    static {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Type type = new TypeToken<ConcurrentHashMap<UUID, String>>() {
        }.getType();
        gsonBuilder.registerTypeAdapter(type, new ConcurrentHashMapTypeAdapter<UUID, String>());
        gson = gsonBuilder.create();
    }
    public static Gson gson() {
        return gson;
    }
}
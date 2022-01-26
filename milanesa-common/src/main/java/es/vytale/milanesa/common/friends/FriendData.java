package es.vytale.milanesa.common.friends;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.Data;

import java.util.UUID;
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

    public static Gson gson() {
        return new Gson();
    }
}
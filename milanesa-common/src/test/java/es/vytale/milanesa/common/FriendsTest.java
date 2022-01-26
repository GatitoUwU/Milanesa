package es.vytale.milanesa.common;

import com.google.gson.Gson;
import es.vytale.milanesa.common.friends.FriendData;
import org.junit.Test;

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
public class FriendsTest {

    @Test
    public void doTest() {
        System.out.println("--------------------------");
        UUID holder = UUID.randomUUID();
        UUID following1 = UUID.randomUUID();
        String following1_ = "uwu1";
        UUID follower1 = UUID.randomUUID();
        String follower1_ = "uwu2";

        FriendData friendProfile = new FriendData(holder, getFrom(following1, following1_), getFrom(follower1, follower1_));

        Gson gson = FriendData.gson();

        String json = gson.toJson(friendProfile);
        System.out.println("Serializing: " + json);

        FriendData deserialized = gson.fromJson(json, FriendData.class);

        System.out.println("");
        System.out.println("Attempting to deserialize:");

        System.out.println("Deserialized: " + deserialized + " and maps are: " + deserialized.getFollowers().getClass().getSimpleName() + " " + deserialized.getFollowing().getClass().getSimpleName());
        System.out.println("--------------------------");
    }

    public ConcurrentMap<UUID, String> getFrom(UUID a, String a2) {
        ConcurrentMap<UUID, String> map = new ConcurrentHashMap<>();
        map.put(a, a2);
        return map;
    }

}
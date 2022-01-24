package es.vytale.milanesa.common;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import es.vytale.milanesa.common.friends.FriendProfile;
import es.vytale.milanesa.common.gson.ConcurrentHashMapTypeAdapter;
import org.junit.Test;

import java.lang.reflect.Type;
import java.util.Map;
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

        FriendProfile friendProfile = new FriendProfile(holder, getFrom(following1_, following1), getFrom(follower1_, follower1));

        Gson gson = FriendProfile.gson();

        String json = gson.toJson(friendProfile);
        System.out.println("Serializing: "+json);

        FriendProfile deserialized = gson.fromJson(json, FriendProfile.class);

        System.out.println("");
        System.out.println("Attempting to deserialize:");

        System.out.println("Deserialized: "+deserialized+ " and maps are: "+deserialized.getFollowers().getClass().getSimpleName()+" "+deserialized.getFollowing().getClass().getSimpleName());
        System.out.println("--------------------------");
    }

    public ConcurrentMap<String, UUID> getFrom(String a, UUID a2) {
        ConcurrentMap<String, UUID> map =  new ConcurrentHashMap<>();
        map.put(a, a2);
        return map;
    }

}
package es.vytale.milanesa.common.user;

import com.google.gson.Gson;
import es.vytale.milanesa.common.friends.FriendProfile;
import es.vytale.milanesa.common.objects.DatableObject;
import lombok.Getter;
import lombok.Setter;

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
@Setter
public class User<T> extends DatableObject {
    private T player;
    private FriendProfile friendProfile;

    public User(UUID uuid) {
        super(uuid);
    }

    @Override
    public void onDownload() {
        if (hasData("friends")) {
            friendProfile = FriendProfile.gson().fromJson(getData("friends"), FriendProfile.class);
        } else {
            friendProfile = new FriendProfile(getUuid(), new ConcurrentHashMap<>(), new ConcurrentHashMap<>());
            putData("friends", FriendProfile.gson().toJson(friendProfile)); // saving default profile.
        }
    }
}
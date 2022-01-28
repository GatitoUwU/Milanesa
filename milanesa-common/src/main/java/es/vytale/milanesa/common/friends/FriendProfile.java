package es.vytale.milanesa.common.friends;

import es.vytale.milanesa.common.objects.DatableObject;
import lombok.Getter;

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
public class FriendProfile extends DatableObject {

    private FriendData friendData;

    public FriendProfile(UUID uuid) {
        super(uuid);
    }

    @Override
    public void beforeUpload() {
        putData("data", FriendData.gson().toJson(friendData)); // saving default profile.
    }

    @Override
    public void afterUpload() {
    }

    @Override
    public void onDownload() {
        if (hasData("data")) {
            friendData = FriendData.gson().fromJson(getData("data"), FriendData.class);
        } else {
            friendData = new FriendData(getUuid(), new ConcurrentHashMap<>(), new ConcurrentHashMap<>());
            beforeUpload();
        }
    }
}

package es.vytale.milanesa.common.user;

import es.vytale.milanesa.common.objects.DatableObject;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

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

    private boolean friendsLock = false;

    public User(UUID uuid) {
        super(uuid);
    }

    @Override
    public void beforeUpload() {
    }

    @Override
    public void afterUpload() {
    }

    @Override
    public void onDownload() {
    }
}
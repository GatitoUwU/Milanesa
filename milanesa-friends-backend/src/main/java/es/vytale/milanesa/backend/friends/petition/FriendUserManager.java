package es.vytale.milanesa.backend.friends.petition;

import es.vytale.milanesa.common.objects.ExpirableCachedManager;

import java.time.Duration;
import java.util.UUID;

/**
 * This code has been created by
 * gatogamer#6666 A.K.A. gatogamer.
 * If you want to use my code, please
 * don't remove this messages and
 * give me the credits. Arigato! n.n
 */
public class FriendUserManager extends ExpirableCachedManager<UUID, FriendUser> {
    public FriendUserManager () {
        super(Duration.ofMinutes(5)); // Remove from cache after 5 minutes of not being used.
    }

    @Override
    public FriendUser getOrCreate(UUID uuid) {
        return get(uuid).orElse(construct(uuid));
    }

    private FriendUser construct(UUID uuid) {
        FriendUser friendUser = new FriendUser(uuid);
        put(uuid, friendUser);
        return friendUser;
    }
}
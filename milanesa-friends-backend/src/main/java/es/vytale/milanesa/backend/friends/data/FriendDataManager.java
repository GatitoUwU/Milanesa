package es.vytale.milanesa.backend.friends.data;

import es.vytale.milanesa.common.friends.FriendProfile;
import es.vytale.milanesa.common.objects.NormalCachedManager;

import java.util.UUID;

/**
 * This code has been created by
 * gatogamer#6666 A.K.A. gatogamer.
 * If you want to use my code, please
 * don't remove this messages and
 * give me the credits. Arigato! n.n
 */
public class FriendDataManager extends NormalCachedManager<UUID, FriendProfile> {
    @Override
    public FriendProfile getOrCreate(UUID uuid) {
        return getValues().computeIfAbsent(uuid, FriendProfile::new);
    }
}
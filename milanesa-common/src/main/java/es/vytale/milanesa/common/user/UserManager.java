package es.vytale.milanesa.common.user;

import es.vytale.milanesa.common.objects.CachedManager;

import java.util.UUID;

/**
 * This code has been created by
 * gatogamer#6666 A.K.A. gatogamer.
 * If you want to use my code, please
 * don't remove this messages and
 * give me the credits. Arigato! n.n
 */
public class UserManager<T> extends CachedManager<UUID, User<T>> {
    @Override
    public User<T> getOrCreate(UUID uuid) {
        return getValues().computeIfAbsent(uuid, ignored -> new User<>(uuid));
    }
}
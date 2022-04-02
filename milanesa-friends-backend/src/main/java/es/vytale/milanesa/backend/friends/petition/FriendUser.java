package es.vytale.milanesa.backend.friends.petition;

import lombok.RequiredArgsConstructor;

import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * This code has been created by
 * gatogamer#6666 A.K.A. gatogamer.
 * If you want to use my code, please
 * don't remove this messages and
 * give me the credits. Arigato! n.n
 */
@RequiredArgsConstructor
public class FriendUser {
    private final UUID uuid;
    private final Set<UUID> asked = ConcurrentHashMap.newKeySet();
}
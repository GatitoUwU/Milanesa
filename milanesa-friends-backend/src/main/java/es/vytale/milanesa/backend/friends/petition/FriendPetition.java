package es.vytale.milanesa.backend.friends.petition;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

/**
 * This code has been created by
 * gatogamer#6666 A.K.A. gatogamer.
 * If you want to use my code, please
 * don't remove this messages and
 * give me the credits. Arigato! n.n
 */
@Getter
@RequiredArgsConstructor
public class FriendPetition {
    private final UUID holder;
    private final UUID other;
    private final long made = System.currentTimeMillis();
}
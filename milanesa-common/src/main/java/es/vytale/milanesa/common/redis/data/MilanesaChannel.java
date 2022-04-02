package es.vytale.milanesa.common.redis.data;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * This code has been created by
 * gatogamer#6666 A.K.A. gatogamer.
 * If you want to use my code, please
 * don't remove this messages and
 * give me the credits. Arigato! n.n
 */
@RequiredArgsConstructor
@Getter
public abstract class MilanesaChannel {
    private final String channel;

    public abstract void handle(MilanesaMessage milanesaMessage);
}
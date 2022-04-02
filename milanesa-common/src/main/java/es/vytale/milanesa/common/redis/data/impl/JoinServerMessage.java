package es.vytale.milanesa.common.redis.data.impl;

import lombok.Data;

import java.util.UUID;

/**
 * This code has been created by
 * gatogamer#6666 A.K.A. gatogamer.
 * If you want to use my code, please
 * don't remove this messages and
 * give me the credits. Arigato! n.n
 */
@Data
public class JoinServerMessage {
    private final UUID uuid;
    private final String name;
}
package es.vytale.milanesa.common.proxy;

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
public class PlayerUpdateData {
    private final String name;
    private final UUID uuid;
    private final String proxy;
    private final PlayerUpdateType playerUpdateType;
}
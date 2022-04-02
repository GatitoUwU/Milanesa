package es.vytale.milanesa.common.server;

import lombok.Data;

/**
 * This code has been created by
 * gatogamer#6666 A.K.A. gatogamer.
 * If you want to use my code, please
 * don't remove this messages and
 * give me the credits. Arigato! n.n
 */
@Data
public class ServerRegisterMessage {
    private final String server;
    private final String section;
    private final String address;
    private final int port;
}
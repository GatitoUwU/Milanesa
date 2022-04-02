package es.vytale.milanesa.velocity.balancer;

import lombok.Data;

import java.util.Arrays;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * This code has been created by
 * gatogamer#6666 A.K.A. gatogamer.
 * If you want to use my code, please
 * don't remove this messages and
 * give me the credits. Arigato! n.n
 */
@Data
public class BalancerData {
    private final String name = "lobbies";
    private final ConcurrentSkipListSet<String> servers = new ConcurrentSkipListSet<>(Arrays.asList("lobby", "lobby2"));
}
package es.vytale.milanesa.velocity.limbo;

import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.server.RegisteredServer;
import es.vytale.milanesa.velocity.Milanesa;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;

import java.util.Queue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * This code has been created by
 * gatogamer#6666 A.K.A. gatogamer.
 * If you want to use my code, please
 * don't remove this messages and
 * give me the credits. Arigato! n.n
 */
@RequiredArgsConstructor
public class LimboQueueProcessor {
    private final Milanesa milanesa;

    public void process() {
        Queue<Player> queue = milanesa.getLimboManager().getQueue();
        AtomicInteger joining = new AtomicInteger();

        while (true) {
            RegisteredServer registeredServer = milanesa.getBalancerManager().getDefaultServer(joining.get());

            if (registeredServer == null) {
                break; // break if the lobby server is null;
            }

            if (queue == null || queue.isEmpty()) {
                break; // break the while if queue is empty qwq
            }

            Player player = queue.peek();
            joining.incrementAndGet();
            player.sendMessage(Component.text("Enviándote."));
            player.createConnectionRequest(registeredServer).connect().thenAccept(result -> {
                if (result.isSuccessful()) {
                    queue.remove(player);
                }
            });
        }
    }
}
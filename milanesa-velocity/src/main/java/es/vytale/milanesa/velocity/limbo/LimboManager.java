package es.vytale.milanesa.velocity.limbo;

import com.velocitypowered.api.proxy.Player;
import es.vytale.milanesa.velocity.Milanesa;
import lombok.Getter;
import lombok.SneakyThrows;
import net.elytrium.limboapi.api.Limbo;
import net.elytrium.limboapi.api.LimboFactory;
import net.elytrium.limboapi.api.chunk.Dimension;
import net.elytrium.limboapi.api.chunk.VirtualWorld;
import net.elytrium.limboapi.api.file.SchematicFile;

import java.io.File;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;

/**
 * This code has been created by
 * gatogamer#6666 A.K.A. gatogamer.
 * If you want to use my code, please
 * don't remove this messages and
 * give me the credits. Arigato! n.n
 */
@Getter
public class LimboManager {
    private final Limbo limbo;
    private final LimboFactory limboFactory;
    private final VirtualWorld limboWorld;
    private final Queue<Player> queue;
    private final LimboQueueProcessor limboQueueProcessor;

    @SneakyThrows
    public LimboManager(Milanesa milanesa) {
        limboFactory = (LimboFactory) milanesa.getProxyServer().getPluginManager().getPlugin("limboapi").orElse(null).getInstance().orElse(null);

        if (limboFactory == null) {
            milanesa.getLogger().error("Cannot bootstrap LimboManager as LimboAPI is null...");
            System.exit(0);
        }

        limboWorld = limboFactory.createVirtualWorld(Dimension.OVERWORLD, 0, 100, 0, 0, 0);
        File file = new File(milanesa.getDataFolder().toFile(), "auth.schematic");
        if (file.exists()) {
            new SchematicFile(file.toPath()).toWorld(limboFactory, limboWorld, 0, 100, 0);
        }
        limbo = limboFactory.createLimbo(limboWorld).setName("VytaleLimbo");

        queue = new ConcurrentLinkedQueue<>();

        limboQueueProcessor = new LimboQueueProcessor(milanesa);
        milanesa.getProxyServer().getScheduler().buildTask(milanesa, limboQueueProcessor::process).repeat(2L, TimeUnit.SECONDS).schedule();
    }

    public void connect(Player player) {
        limbo.spawnPlayer(player, new MilanesaLimboSessionHandler());
    }
}
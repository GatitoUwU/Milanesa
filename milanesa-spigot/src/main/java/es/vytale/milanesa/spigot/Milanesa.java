package es.vytale.milanesa.spigot;

import es.vytale.milanesa.common.executor.NekoExecutor;
import es.vytale.milanesa.common.redis.MilanesaMessageHandler;
import es.vytale.milanesa.common.redis.RedisHandler;
import es.vytale.milanesa.common.redis.credentials.MilanesaRedisCredentials;
import es.vytale.milanesa.common.redis.data.MilanesaChannel;
import es.vytale.milanesa.common.redis.data.MilanesaMessage;
import es.vytale.milanesa.common.storage.MongoHandler;
import es.vytale.milanesa.common.storage.credentials.MilanesaMongoCredentials;
import es.vytale.milanesa.common.user.UserDataAccessor;
import es.vytale.milanesa.common.user.UserManager;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;

@Getter
public class Milanesa extends JavaPlugin {

    private NekoExecutor nekoExecutor;

    private RedisHandler redisHandler;
    private MilanesaMessageHandler milanesaMessageHandler;

    private MongoHandler mongoHandler;

    private UserManager<Player> userManager;
    private UserDataAccessor<Player> userDataAccessor;

    @Override
    public void onEnable() {
        getDataFolder().mkdir();

        nekoExecutor = new NekoExecutor();

        redisHandler = new RedisHandler(MilanesaRedisCredentials.fromFile(getFile("redis-credentials.json")));
        milanesaMessageHandler = new MilanesaMessageHandler(redisHandler);

        mongoHandler = new MongoHandler(MilanesaMongoCredentials.fromFile(getFile("mongo-credentials.json")));

        userManager = new UserManager<>();
        userDataAccessor = new UserDataAccessor<>(mongoHandler.getDatabase());

        milanesaMessageHandler.registerChannel(new MilanesaChannel("pong") {
            @Override
            public void handle(MilanesaMessage milanesaMessage) {
                System.out.println("Received pong from Velocity!");
            }
        });

        new BukkitRunnable() {
            @Override
            public void run() {
                milanesaMessageHandler.sendMessage(new MilanesaMessage("ping", "no data xd"));
            }
        }.runTaskLater(this, 20L);
    }

    public File getFile(String file) {
        return new File(getDataFolder(), file);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}

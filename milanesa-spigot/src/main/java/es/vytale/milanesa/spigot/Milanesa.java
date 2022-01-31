package es.vytale.milanesa.spigot;

import es.vytale.milanesa.common.executor.NekoExecutor;
import es.vytale.milanesa.common.friends.FriendProfileAccessor;
import es.vytale.milanesa.common.proxy.ProxyManager;
import es.vytale.milanesa.common.redis.MilanesaMessageHandler;
import es.vytale.milanesa.common.redis.RedisHandler;
import es.vytale.milanesa.common.redis.credentials.MilanesaRedisCredentials;
import es.vytale.milanesa.common.redis.data.MilanesaChannel;
import es.vytale.milanesa.common.redis.data.MilanesaMessage;
import es.vytale.milanesa.common.server.ServerRegisterMessage;
import es.vytale.milanesa.common.storage.MongoHandler;
import es.vytale.milanesa.common.storage.credentials.MilanesaMongoCredentials;
import es.vytale.milanesa.common.user.UserDataAccessor;
import es.vytale.milanesa.common.user.UserManager;
import es.vytale.milanesa.spigot.commands.FriendsCommand;
import es.vytale.milanesa.spigot.listeners.ConnectionListener;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.concurrent.atomic.AtomicLong;

@Getter
public class Milanesa extends JavaPlugin {

    private NekoExecutor nekoExecutor;

    private RedisHandler redisHandler;
    private MilanesaMessageHandler milanesaMessageHandler;

    private MongoHandler mongoHandler;

    private UserManager<Player> userManager;
    private UserDataAccessor<Player> userDataAccessor;

    private FriendProfileAccessor friendProfileAccessor;

    private ProxyManager proxyManager;

    @Override
    public void onEnable() {
        getDataFolder().mkdir();

        nekoExecutor = new NekoExecutor();

        redisHandler = new RedisHandler(MilanesaRedisCredentials.fromFile(getFile("redis-credentials.json")));
        milanesaMessageHandler = new MilanesaMessageHandler(redisHandler);

        mongoHandler = new MongoHandler(MilanesaMongoCredentials.fromFile(getFile("mongo-credentials.json")));

        userManager = new UserManager<>();
        userDataAccessor = new UserDataAccessor<>(mongoHandler.getDatabase());
        friendProfileAccessor = new FriendProfileAccessor(mongoHandler.getDatabase());

        AtomicLong handled = new AtomicLong();

        getCommand("test-redis-latency").setExecutor((sender, command, label, args) -> {
            if (sender.hasPermission("milanesa.admin")) {
                handled.set(0L);
                milanesaMessageHandler.registerChannel(new MilanesaChannel("pong") {
                    @Override
                    public void handle(MilanesaMessage milanesaMessage) {
                        sender.sendMessage("Processed " + handled.incrementAndGet() + " pongs!");
                    }
                });

                for (int i = 0; i < 10_000; i++) {
                    nekoExecutor.submit(() -> {
                        milanesaMessageHandler.sendMessage(new MilanesaMessage("ping", "no data xd"));
                    });
                }
            }
            return false;
        });
        getCommand("friends").setExecutor(new FriendsCommand(this));

        proxyManager = new ProxyManager(null, nekoExecutor, milanesaMessageHandler);

        Bukkit.getPluginManager().registerEvents(new ConnectionListener(this), this);
    }

    public File getFile(String file) {
        return new File(getDataFolder(), file);
    }

    @Override
    public void onDisable() {
        Bukkit.getOnlinePlayers().forEach(player -> player.kickPlayer(ChatColor.translateAlternateColorCodes('&', "&bMilanesa &8> &7Te hemos expulsado debido a que el servidor se está reiniciando.")));
        redisHandler.kill();
        milanesaMessageHandler.kill();
    }
}

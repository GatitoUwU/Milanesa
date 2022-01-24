package es.vytale.milanesa.velocity;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.ProxyServer;
import es.vytale.milanesa.common.executor.NekoExecutor;
import es.vytale.milanesa.common.proxy.ProxyManager;
import es.vytale.milanesa.common.redis.MilanesaMessageHandler;
import es.vytale.milanesa.common.redis.RedisHandler;
import es.vytale.milanesa.common.redis.credentials.MilanesaRedisCredentials;
import es.vytale.milanesa.common.redis.data.MilanesaChannel;
import es.vytale.milanesa.common.redis.data.MilanesaMessage;
import es.vytale.milanesa.velocity.listeners.ConnectionListener;
import es.vytale.milanesa.velocity.listeners.PingListener;
import org.slf4j.Logger;

import javax.inject.Inject;
import java.io.File;
import java.nio.file.Path;

@Plugin(
        id = "milanesa",
        name = "Milanesa",
        version = "1.0-SNAPSHOT",
        description = "Núcleo principal del servidor, versión para Velocity",
        url = "https://vytale.es",
        authors = {"change-me-later"}
)
public class Milanesa {
    @Inject
    private Logger logger;

    @Inject
    @DataDirectory
    private Path dataFolder;

    @Inject
    private ProxyServer proxyServer;

    private NekoExecutor nekoExecutor;
    private RedisHandler redisHandler;
    private MilanesaMessageHandler milanesaMessageHandler;

    private ProxyManager proxyManager;

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
        dataFolder.toFile().mkdir();

        String proxyId = System.getProperty("milanesa-proxyName");

        if (proxyId == null || proxyId.isEmpty()) {
            logger.error("[ERROR] Milanesa cannot be bootstraped without a proxyId, specify it on Java arguments by using -Dmilanesa-proxyName=<proxyName> before your -jar argument! Milanesa will quit with 0 error code.");
            System.exit(0);
            return;
        }

        nekoExecutor = new NekoExecutor();

        redisHandler = new RedisHandler(MilanesaRedisCredentials.fromFile(getFile("redis-credentials.json")));
        milanesaMessageHandler = new MilanesaMessageHandler(redisHandler);

        milanesaMessageHandler.registerChannel(new MilanesaChannel("ping") {
            @Override
            public void handle(MilanesaMessage milanesaMessage) {
                nekoExecutor.submit(() -> milanesaMessageHandler.sendMessage(new MilanesaMessage("pong", String.valueOf(System.currentTimeMillis()))));
            }
        });

        proxyManager = new ProxyManager(proxyId, nekoExecutor, milanesaMessageHandler);

        proxyServer.getEventManager().register(this, new ConnectionListener(nekoExecutor, proxyManager));
        proxyServer.getEventManager().register(this, new PingListener(proxyManager));
    }


    public File getFile(String file) {
        return new File(dataFolder.toFile(), file);
    }
}
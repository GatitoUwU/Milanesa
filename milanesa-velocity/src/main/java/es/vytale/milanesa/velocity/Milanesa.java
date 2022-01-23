package es.vytale.milanesa.velocity;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import es.vytale.milanesa.common.redis.MilanesaMessageHandler;
import es.vytale.milanesa.common.redis.RedisHandler;
import es.vytale.milanesa.common.redis.credentials.MilanesaRedisCredentials;
import es.vytale.milanesa.common.redis.data.MilanesaChannel;
import es.vytale.milanesa.common.redis.data.MilanesaMessage;
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
        authors = {"gatogamer"}
)
public class Milanesa {
    @Inject
    private Logger logger;

    @Inject
    @DataDirectory
    private Path dataFolder;

    private RedisHandler redisHandler;
    private MilanesaMessageHandler milanesaMessageHandler;

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
        dataFolder.toFile().mkdir();

        redisHandler = new RedisHandler(MilanesaRedisCredentials.fromFile(getFile("redis-credentials.json")));
        milanesaMessageHandler = new MilanesaMessageHandler(redisHandler);

        milanesaMessageHandler.registerChannel(new MilanesaChannel("ping") {
            @Override
            public void handle(MilanesaMessage milanesaMessage) {
                milanesaMessageHandler.sendMessage(new MilanesaMessage("pong", String.valueOf(System.currentTimeMillis())));
            }
        });
    }


    public File getFile(String file) {
        return new File(dataFolder.toFile(), file);
    }
}
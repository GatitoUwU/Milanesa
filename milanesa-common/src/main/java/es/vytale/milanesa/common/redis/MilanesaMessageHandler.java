package es.vytale.milanesa.common.redis;

import com.google.gson.Gson;
import es.vytale.milanesa.common.redis.data.MilanesaChannel;
import es.vytale.milanesa.common.redis.data.MilanesaMessage;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * This code has been created by
 * gatogamer#6666 A.K.A. gatogamer.
 * If you want to use my code, please
 * don't remove this messages and
 * give me the credits. Arigato! n.n
 */
public class MilanesaMessageHandler {
    private final RedisHandler redisHandler;
    private final Gson gson;
    private final Map<String, MilanesaChannel> channels = new ConcurrentHashMap<>();
    private final String channel;

    public MilanesaMessageHandler(RedisHandler redisHandler) {
        this.redisHandler = redisHandler;
        this.gson = new Gson();
        this.channel = redisHandler.getCreds().getChannel();

        registerRedis();
    }

    public void handleDeserialization(String data) {
        MilanesaMessage milanesaMessage = gson.fromJson(data, MilanesaMessage.class);

        MilanesaChannel milanesaChannel = channels.get(milanesaMessage.getChannel());

        if (milanesaChannel != null) {
            milanesaChannel.handle(milanesaMessage);
        }
    }

    public void sendMessage(String subChannel, String data) {
        sendMessage(new MilanesaMessage(subChannel, data));
    }

    public void sendMessage(MilanesaMessage milanesaMessage) {
        try (Jedis jedis = redisHandler.getJedisPool().getResource()) {
            jedis.publish(redisHandler.getCreds().getChannel(), gson.toJson(milanesaMessage));
        }
    }

    public void registerChannel(MilanesaChannel channel) {
        channels.put(channel.getChannel(), channel);
    }

    private void registerRedis() {
        redisHandler.getJedisPool().getResource().subscribe(new JedisPubSub() {
            @Override
            public void onMessage(String channel, String message) {
                handleDeserialization(message);
            }
        }, channel);
    }
}
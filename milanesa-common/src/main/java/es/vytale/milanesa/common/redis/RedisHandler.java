package es.vytale.milanesa.common.redis;

import es.vytale.milanesa.common.redis.credentials.MilanesaRedisCredentials;
import lombok.Getter;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * This code has been created by
 * gatogamer#6666 A.K.A. gatogamer.
 * If you want to use my code, please
 * don't remove this messages and
 * give me the credits. Arigato! n.n
 */
@Getter
public class RedisHandler {
    private final MilanesaRedisCredentials creds;
    private final JedisPool jedisPool;

    public RedisHandler(MilanesaRedisCredentials creds) {
        this.creds = creds;
        JedisPoolConfig jpc = new JedisPoolConfig();
        jpc.setMaxTotal(creds.getPoolSize());

        jedisPool = new JedisPool(jpc, creds.getHost(), creds.getPort(), creds.getTimeout(), creds.isAuthentication() ? creds.getPassword() : null);
    }

    public void kill() {
        try {
            jedisPool.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            jedisPool.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
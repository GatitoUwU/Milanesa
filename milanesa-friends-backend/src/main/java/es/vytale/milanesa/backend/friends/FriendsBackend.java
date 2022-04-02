package es.vytale.milanesa.backend.friends;

import com.google.gson.Gson;
import es.vytale.milanesa.backend.friends.data.FriendDataManager;
import es.vytale.milanesa.backend.friends.petition.FriendPetitionManager;
import es.vytale.milanesa.backend.friends.petition.FriendUserManager;
import es.vytale.milanesa.common.executor.NekoExecutor;
import es.vytale.milanesa.common.friends.FriendProfileAccessor;
import es.vytale.milanesa.common.redis.MilanesaMessageHandler;
import es.vytale.milanesa.common.redis.RedisHandler;
import es.vytale.milanesa.common.redis.credentials.MilanesaRedisCredentials;
import es.vytale.milanesa.common.storage.MongoHandler;
import es.vytale.milanesa.common.storage.credentials.MilanesaMongoCredentials;
import lombok.Getter;

import java.io.File;

/**
 * This code has been created by
 * gatogamer#6666 A.K.A. gatogamer.
 * If you want to use my code, please
 * don't remove this messages and
 * give me the credits. Arigato! n.n
 */
@Getter
public class FriendsBackend {

    @Getter
    private static final Gson gson = new Gson();

    private final NekoExecutor nekoExecutor;

    private final RedisHandler redisHandler;
    private final MilanesaMessageHandler milanesaMessageHandler;

    private final MongoHandler mongoHandler;

    private final FriendDataManager friendDataManager;
    private final FriendProfileAccessor friendProfileAccessor;

    private final FriendPetitionManager friendPetitionManager;
    private final FriendUserManager friendUserManager;

    public FriendsBackend () {
        File folder = new File("configurations");
        folder.mkdir();

        nekoExecutor = new NekoExecutor();

        redisHandler = new RedisHandler(MilanesaRedisCredentials.fromFile(new File(folder, "redis-credentials.json")));
        milanesaMessageHandler = new MilanesaMessageHandler(redisHandler);

        mongoHandler = new MongoHandler(MilanesaMongoCredentials.fromFile(new File(folder, "mongo-credentials.json")));

        friendDataManager = new FriendDataManager();
        friendProfileAccessor = new FriendProfileAccessor(mongoHandler.getDatabase());

        friendPetitionManager = new FriendPetitionManager();
        friendUserManager = new FriendUserManager();
    }
}
package es.vytale.milanesa.common.friends;

import com.mongodb.client.MongoDatabase;
import es.vytale.milanesa.common.storage.MongoDataAccessor;

/**
 * This code has been created by
 * gatogamer#6666 A.K.A. gatogamer.
 * If you want to use my code, please
 * don't remove this messages and
 * give me the credits. Arigato! n.n
 */
public class FriendProfileAccessor extends MongoDataAccessor<FriendProfile> {
    public FriendProfileAccessor(MongoDatabase database) {
        super(database, "friends");
    }
}
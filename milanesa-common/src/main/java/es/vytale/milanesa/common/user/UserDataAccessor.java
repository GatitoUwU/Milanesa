package es.vytale.milanesa.common.user;

import com.mongodb.client.MongoDatabase;
import es.vytale.milanesa.common.storage.MongoDataAccessor;

/**
 * This code has been created by
 * gatogamer#6666 A.K.A. gatogamer.
 * If you want to use my code, please
 * don't remove this messages and
 * give me the credits. Arigato! n.n
 */
public class UserDataAccessor<T> extends MongoDataAccessor<User<T>> {
    public UserDataAccessor(MongoDatabase database) {
        super(database, "usersData");
    }
}

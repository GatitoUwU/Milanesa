package es.vytale.milanesa.common.storage;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoDatabase;
import es.vytale.milanesa.common.storage.credentials.MilanesaMongoCredentials;
import lombok.Getter;

import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This code has been created by
 * gatogamer#6666 A.K.A. gatogamer.
 * If you want to use my code, please
 * don't remove this messages and
 * give me the credits. Arigato! n.n
 */
@Getter
public class MongoHandler {
    private boolean connected;

    private MongoClient client;
    private MongoDatabase database;

    public MongoHandler(MilanesaMongoCredentials creds) {
        try {
            if (creds.isUriMode()) {
                MongoClientURI mongoClientURI = new MongoClientURI(creds.getUriModeCredentials().getUriString());
                client = new MongoClient(mongoClientURI);
                database = client.getDatabase(creds.getUriModeCredentials().getUriDb());
            } else {
                MilanesaMongoCredentials.UserCredentials userCreds = creds.getUserCredentials();
                MilanesaMongoCredentials.UserCredentials.Authentication authCreds = userCreds.getAuthentication();
                if (authCreds.isAuthentication()) {
                    MongoCredential mongoCredential = MongoCredential.createCredential(authCreds.getUser(), authCreds.getAuthDatabase(), authCreds.getPassword().toCharArray());
                    client = new MongoClient(new ServerAddress(userCreds.getHost(), userCreds.getPort()), Collections.singletonList(mongoCredential));
                } else {
                    client = new MongoClient(new ServerAddress(userCreds.getHost(), userCreds.getPort()));
                }
                database = client.getDatabase(userCreds.getDatabaseName());
                this.connected = true;
            }
        } catch (Exception e) {
            Logger.getGlobal().log(Level.SEVERE, "Error while bootstraping Mongo database connection, Milanesa cannot work without a Mongo database, shutting down...");
            System.exit(0);
        }
    }
}
package es.vytale.milanesa.common.storage.credentials;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import es.vytale.milanesa.common.io.FileIO;
import lombok.Data;
import lombok.SneakyThrows;

import java.io.File;

/**
 * This code has been created by
 * gatogamer#6666 A.K.A. gatogamer.
 * If you want to use my code, please
 * don't remove this messages and
 * give me the credits. Arigato! n.n
 */
@Data
public class MilanesaMongoCredentials {
    private boolean uriMode = false;
    private UserCredentials userCredentials = new UserCredentials();
    private UriModeCredentials uriModeCredentials = new UriModeCredentials();

    @SneakyThrows
    public static MilanesaMongoCredentials fromFile(File file) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        String json;
        if (file.createNewFile()) {
            json = gson.toJson(new MilanesaMongoCredentials());
            FileIO.writeFile(file, json);
        } else {
            json = FileIO.readFile(file);
        }

        return gson.fromJson(json, MilanesaMongoCredentials.class);
    }

    @Data
    public static class UserCredentials {
        private String host = "mongo-server";
        private int port = 27017;
        private String databaseName = "Milanesa";

        private Authentication authentication = new Authentication();

        @Data
        public static class Authentication {
            private boolean authentication = true;
            private String user = "joe";
            private String password = "mamma";
            private String authDatabase = "admin";
        }
    }

    @Data
    public static class UriModeCredentials {
        private String uriString = "";
        private String uriDb = "Milanesa";
    }
}
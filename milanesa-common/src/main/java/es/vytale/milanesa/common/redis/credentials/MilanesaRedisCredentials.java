package es.vytale.milanesa.common.redis.credentials;

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
public class MilanesaRedisCredentials {
    private String host = "redis-server";
    private int port = 6379;
    private int poolSize = 128;
    private int timeout = 30_000;
    private String password = "saludito";
    private String channel = "Milanesa";

    @SneakyThrows
    public static MilanesaRedisCredentials fromFile(File file) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        String json;
        if (file.createNewFile()) {
            json = gson.toJson(new MilanesaRedisCredentials());
            FileIO.writeFile(file, json);
        } else {
            json = FileIO.readFile(file);
        }

        return gson.fromJson(json, MilanesaRedisCredentials.class);
    }

    public boolean isAuthentication() {
        return password != null && !password.isEmpty();
    }
}
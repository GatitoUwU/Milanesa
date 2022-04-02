package es.vytale.milanesa.velocity.balancer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import es.vytale.milanesa.common.io.FileIO;
import lombok.Data;
import lombok.SneakyThrows;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * This code has been created by
 * gatogamer#6666 A.K.A. gatogamer.
 * If you want to use my code, please
 * don't remove this messages and
 * give me the credits. Arigato! n.n
 */
@Data
public class BalancerConfig {
    private final String defaultSection = "lobbies";
    private final List<BalancerData> balancers = Collections.singletonList(new BalancerData());

    @SneakyThrows
    public static BalancerConfig fromFile(File file) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        String json;
        if (file.createNewFile()) {
            json = gson.toJson(new BalancerConfig());
            FileIO.writeFile(file, json);
        } else {
            json = FileIO.readFile(file);
        }

        return gson.fromJson(json, BalancerConfig.class);
    }
}
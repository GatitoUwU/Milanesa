package es.vytale.milanesa.common.redis.data;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import lombok.Data;

/**
 * This code has been created by
 * gatogamer#6666 A.K.A. gatogamer.
 * If you want to use my code, please
 * don't remove this messages and
 * give me the credits. Arigato! n.n
 */
@Data
public class MilanesaMessage {
    private final String channel;
    private final String data;

    public JsonObject asJsonObject() {
        return new Gson().fromJson(data, JsonObject.class);
    }

    public JsonArray asJsonArray() {
        return new Gson().fromJson(data, JsonArray.class);
    }
}
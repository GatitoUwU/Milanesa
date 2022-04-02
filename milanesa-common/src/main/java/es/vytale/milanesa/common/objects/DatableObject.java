package es.vytale.milanesa.common.objects;

import com.google.gson.JsonObject;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * This code has been created by
 * gatogamer#6666 A.K.A. gatogamer.
 * If you want to use my code, please
 * don't remove this messages and
 * give me the credits. Arigato! n.n
 */
@Data
@RequiredArgsConstructor
public abstract class DatableObject {
    private final UUID uuid;
    private final Map<String, String> data = new ConcurrentHashMap<>();
    private boolean downloaded = false;

    public JsonObject asJsonObject() {
        JsonObject jsonObject = new JsonObject();
        data.forEach(jsonObject::addProperty);
        return jsonObject;
    }

    public void fromJson(JsonObject jsonObject) {
        data.clear();
        jsonObject.entrySet().forEach(json -> data.put(json.getKey(), json.getValue().getAsString()));
    }

    public void putData(String key, String value) {
        data.put(key, value);
    }

    public String getData(String key) {
        return data.get(key);
    }

    public String getDataOrDefault(String key, String defaultValue) {
        return data.getOrDefault(key, defaultValue);
    }

    public String getDataOrCreateDefault(String key, String defaultValue) {
        return data.computeIfAbsent(key, ignored -> defaultValue);
    }

    public boolean hasData(String key) {
        return data.containsKey(key);
    }

    public abstract void beforeUpload();

    public abstract void afterUpload();

    public abstract void onDownload();
}
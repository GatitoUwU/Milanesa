package es.vytale.milanesa.common.gson;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * This code has been created by
 * gatogamer#6666 A.K.A. gatogamer.
 * If you want to use my code, please
 * don't remove this messages and
 * give me the credits. Arigato! n.n
 */
public final class ConcurrentHashMapTypeAdapter<K,V> extends TypeAdapter<ConcurrentMap<K,V>> {
    @Override
    public synchronized ConcurrentMap<K,V> read(JsonReader in) throws IOException {
        if (in.peek() == JsonToken.NULL) {
            in.nextNull();
            return null;
        }
        Type type = new TypeToken<LinkedTreeMap<K,V>>() {}.getType();
        Gson gson = new Gson();
        LinkedTreeMap<K,V> ltm = gson.fromJson(in, type);
        return new ConcurrentHashMap<>(ltm);
    }

    @Override
    public synchronized void write(JsonWriter out, ConcurrentMap<K, V> value) throws IOException {
        Gson g = new Gson();
        out.value(g.toJson(value));
    }
}
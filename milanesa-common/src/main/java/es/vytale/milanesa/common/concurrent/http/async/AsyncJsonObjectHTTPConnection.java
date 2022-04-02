package es.vytale.milanesa.common.concurrent.http.async;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import lombok.Getter;
import lombok.Setter;

/**
 * This code has been created by
 * gatogamer#6666 A.K.A. gatogamer.
 * If you want to use my code, please
 * don't remove this messages and
 * give me the credits. Arigato! n.n
 */
@Getter
@Setter
public class AsyncJsonObjectHTTPConnection extends AsyncHTTPConnection<JsonElement> {

    public static String agent = "Mozilla/5.0";

    public AsyncJsonObjectHTTPConnection(String realUrl) {
        super(realUrl);
    }

    @Override
    public String getDefault() {
        return "{}";
    }

    @Override
    public String getType() {
        return "application/json";
    }

    @Override
    public JsonElement get() {
        return new JsonParser().parse(getResponse());
    }
}
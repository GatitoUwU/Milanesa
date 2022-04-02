package es.vytale.milanesa.common.concurrent.http.async;

import com.google.common.util.concurrent.ListeningExecutorService;
import es.vytale.milanesa.common.concurrent.async.AsyncResponse;
import es.vytale.milanesa.common.concurrent.async.BasicAsyncResponse;
import es.vytale.milanesa.common.concurrent.response.BasicResponse;
import es.vytale.milanesa.common.concurrent.response.ResponseStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * This code has been created by
 * gatogamer#6666 A.K.A. gatogamer.
 * If you want to use my code, please
 * don't remove this messages and
 * give me the credits. Arigato! n.n
 */
@Getter
@Setter
public abstract class AsyncHTTPConnection<T> {

    public static String agent = "Mozilla/5.0";

    private String realUrl;
    private String response;

    public AsyncHTTPConnection(String realUrl) {
        this.realUrl = realUrl;
    }

    @SneakyThrows
    protected String make() {
        URL url = new URL(realUrl);

        HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("User-Agent", agent);
        connection.setRequestProperty("Content-type", getType());

        int code = connection.getResponseCode();
        if (code == 200) {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            StringBuilder builder = new StringBuilder();
            bufferedReader.lines().forEach(builder::append);

            bufferedReader.close();

            String httpResponse = builder.toString();

            return httpResponse.isEmpty() ? getDefault() : httpResponse;
        }
        return getDefault();
    }

    public abstract String getDefault();

    public abstract String getType();

    public abstract T get();

    public AsyncResponse<T> ask(ListeningExecutorService listeningExecutorService) {
        return new BasicAsyncResponse<>(
                listeningExecutorService.submit(() -> {
                    setResponse(make());
                    return new BasicResponse<>(ResponseStatus.SUCCESS, get());
                }));
    }
}
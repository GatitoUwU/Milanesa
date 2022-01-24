package es.vytale.milanesa.common.storage;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.ReplaceOptions;
import es.vytale.milanesa.common.concurrent.async.AsyncResponse;
import es.vytale.milanesa.common.concurrent.async.BasicAsyncResponse;
import es.vytale.milanesa.common.concurrent.response.BasicResponse;
import es.vytale.milanesa.common.concurrent.response.Response;
import es.vytale.milanesa.common.concurrent.response.ResponseStatus;
import es.vytale.milanesa.common.executor.NekoExecutor;
import es.vytale.milanesa.common.objects.DatableObject;
import org.bson.Document;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This code has been created by
 * gatogamer#6666 A.K.A. gatogamer.
 * If you want to use my code, please
 * don't remove this messages and
 * give me the credits. Arigato! n.n
 */
public class MongoDataAccessor<T extends DatableObject> {

    private final MongoCollection<Document> dataCollection;

    public MongoDataAccessor(MongoDatabase database, String collectionName) {
        dataCollection = database.getCollection(collectionName);
    }

    public AsyncResponse<T> downloadAsync(NekoExecutor nekoExecutor, T t) {
        return new BasicAsyncResponse<>(
                nekoExecutor.getListeningExecutorService().submit(() -> downloadAsSync(t))
        );
    }

    public Response<T> downloadAsSync (T t) {
        try {
            this.downloadData(t);
        } catch (Exception ex) {
            Logger.getGlobal().log(Level.SEVERE, "Error while download data!", ex);
            return new BasicResponse<>(ResponseStatus.ERROR, t);
        }
        return new BasicResponse<>(ResponseStatus.SUCCESS, t);
    }

    public void uploadData(NekoExecutor nekoExecutor, T t) {
        nekoExecutor.submit(() -> uploadData(t));
    }

    public void uploadData(T t) {
        if (!t.isDownloaded()) {
            return;
        }
        Document document = new Document("uuid", t.getUuid().toString());
        document.put("data", new Gson().toJson(t.asJsonObject()));

        dataCollection.replaceOne(Filters.eq("uuid", t.getUuid().toString()), document, new ReplaceOptions().upsert(true));
    }

    public void downloadData(NekoExecutor nekoExecutor, T t) {
        nekoExecutor.submit(() -> downloadData(t));
    }

    public void downloadData(T t) {
        FindIterable<Document> foundIterator = dataCollection.find(Filters.eq("uuid", t.getUuid().toString()));
        Document found = foundIterator.first();

        if (found == null) {
            t.setDownloaded(true);
            t.onDownload();
            uploadData(t);
            return;
        }

        downloadData(found, t);
    }

    public void downloadData(Document document, T t) {
        t.fromJson(new Gson().fromJson(document.getString("data"), JsonObject.class));
        t.setDownloaded(true);
    }

}
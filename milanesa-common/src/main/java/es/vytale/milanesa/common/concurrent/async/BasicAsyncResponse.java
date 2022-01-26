package es.vytale.milanesa.common.concurrent.async;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import es.vytale.milanesa.common.concurrent.callback.Callback;
import es.vytale.milanesa.common.concurrent.response.Response;
import es.vytale.milanesa.common.executor.NekoExecutor;

/**
 * This code has been created by
 * gatogamer#6666 A.K.A. gatogamer.
 * If you want to use my code, please
 * don't remove this messages and
 * give me the credits. Arigato! n.n
 */
public class BasicAsyncResponse<T> implements AsyncResponse<T> {

    private final ListenableFuture<Response<T>> responseListenableFuture;

    public BasicAsyncResponse(ListenableFuture<Response<T>> responseListenableFuture) {
        this.responseListenableFuture = responseListenableFuture;
    }

    @Override
    public void addCallback(Callback<Response<T>> callback, NekoExecutor nekoExecutor) {
        Futures.addCallback(responseListenableFuture, parseCallback(callback), nekoExecutor.getListeningExecutorService());
    }

    private FutureCallback<Response<T>> parseCallback(Callback<Response<T>> callback) {
        return new FutureCallback<Response<T>>() {
            @Override
            public void onSuccess(Response<T> result) {
                callback.call(result);
            }

            @Override
            public void onFailure(Throwable t) {
                callback.handleError(t);
            }
        };
    }
}

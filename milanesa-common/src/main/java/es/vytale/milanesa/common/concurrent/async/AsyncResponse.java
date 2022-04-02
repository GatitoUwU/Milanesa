package es.vytale.milanesa.common.concurrent.async;

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
public interface AsyncResponse<T> {
    void addCallback(Callback<Response<T>> callback, NekoExecutor nekoExecutor);
}
package es.vytale.milanesa.common.concurrent.callback;

/**
 * This code has been created by
 * gatogamer#6666 A.K.A. gatogamer.
 * If you want to use my code, please
 * don't remove this messages and
 * give me the credits. Arigato! n.n
 */
public interface Callback<T> {

    void call(T object);

    default void handleError(Throwable throwable) {
        throwable.printStackTrace();
    }

}

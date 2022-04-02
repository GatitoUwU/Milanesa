package es.vytale.milanesa.common.concurrent.response;

/**
 * This code has been created by
 * gatogamer#6666 A.K.A. gatogamer.
 * If you want to use my code, please
 * don't remove this messages and
 * give me the credits. Arigato! n.n
 */

import java.util.Optional;
import java.util.function.Consumer;

public class BasicResponse<T> implements Response<T> {

    private final ResponseStatus status;
    private final T response;

    public BasicResponse(ResponseStatus status, T response) {
        this.status = status;
        this.response = response;
    }

    @Override
    public ResponseStatus getStatus() {
        return status;
    }

    @Override
    public Optional<T> getResponse() {
        return Optional.ofNullable(response);
    }

    @Override
    public boolean wasSuccessfully() {
        return (status == ResponseStatus.SUCCESS && response != null);
    }

    @Override
    public void ifSuccessful(Consumer<Optional<T>> consumer) {
        if (!wasSuccessfully()) return;
        consumer.accept(getResponse());
    }
}
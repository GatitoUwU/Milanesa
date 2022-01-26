package es.vytale.milanesa.common.concurrent.response;

import java.util.Optional;
import java.util.function.Consumer;

/**
 * This code has been created by
 * gatogamer#6666 A.K.A. gatogamer.
 * If you want to use my code, please
 * don't remove this messages and
 * give me the credits. Arigato! n.n
 */
public interface Response<T> {

    /**
     * Used to get the status of the response
     *
     * @return The status response
     */

    ResponseStatus getStatus();

    /**
     * An optional response of the object generic type
     *
     * @return The optional response
     */

    Optional<T> getResponse();

    /**
     * Represents the completion of the response, and a check if the response was successful
     *
     * @return A boolean representing if the response was obtained or not
     */

    boolean wasSuccessfully();

    /**
     * A consumer accepted when the response was successfully obtained
     *
     * @param consumer The consumer to use to check if the response was successfully obtained
     */

    void ifSuccessful(Consumer<Optional<T>> consumer);


}

package es.vytale.milanesa.common.executor;

import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import lombok.Getter;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * This code has been created by
 * gatogamer#6666 A.K.A. gatogamer.
 * If you want to use my code, please
 * don't remove this messages and
 * give me the credits. Arigato! n.n
 */
@Getter
public class NekoExecutor {
    private final int threads = Integer.getInteger("milanesa-threads-size", Runtime.getRuntime().availableProcessors() * 2);
    private final int queueSize = Integer.getInteger("milanesa-queueSize", 1500);
    private final ListeningExecutorService listeningExecutorService;

    public NekoExecutor() {
        System.out.println("Firing up a NekoExecutor with " + threads + " threads and a queue size of " + queueSize + "!");
        listeningExecutorService = MoreExecutors.listeningDecorator(
                new ThreadPoolExecutor(threads, threads, 0L, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(queueSize))
        );
    }

    public void submit(Runnable runnable) {
        listeningExecutorService.submit(runnable);
    }
}

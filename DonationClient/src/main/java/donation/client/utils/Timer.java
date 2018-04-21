package donation.client.utils;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;

public class Timer {
    public static void setTimeout(Runnable task, int millis) {
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        executor.schedule(task, millis, TimeUnit.MILLISECONDS);
        executor.shutdown();
    }
}

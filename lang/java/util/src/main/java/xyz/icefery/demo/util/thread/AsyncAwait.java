package xyz.icefery.demo.util.thread;

import java.util.List;
import java.util.concurrent.CountDownLatch;

public class AsyncAwait {
    public static void awaitAll(List<Runnable> tasks) throws Exception {
        CountDownLatch countDownLatch = new CountDownLatch(tasks.size());
        for (Runnable task : tasks) {
            try {
                task.run();
            } finally {
                countDownLatch.countDown();
            }
        }
        countDownLatch.await();
    }
}

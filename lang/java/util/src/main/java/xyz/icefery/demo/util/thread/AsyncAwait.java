package xyz.icefery.demo.util.thread;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadPoolExecutor;

public class AsyncAwait {
    public static void awaitAll(ThreadPoolExecutor pool, List<Runnable> tasks) throws Exception {
        CountDownLatch countDownLatch = new CountDownLatch(tasks.size());
        for (Runnable task : tasks) {
            pool.execute(() -> {
                try {
                    task.run();
                } finally {
                    countDownLatch.countDown();
                }
            });
        }
        countDownLatch.await();
    }
}

package xyz.icefery.demo.util.sql;

import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.icefery.demo.util.function.ThrowableRunnable;
import xyz.icefery.demo.util.thread.AsyncAwait;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.function.Supplier;

public class NativeBatchExecutor {
    private static final Logger log = LoggerFactory.getLogger(NativeBatchExecutor.class);

    public static <T> ThrowableRunnable<Exception> batchInsert(Supplier<Connection> connectionGetter, ThreadPoolExecutor executor, String dialect, Class<T> cls, List<T> data, int batchSize) throws Exception {
        // 开始时间
        long start = System.currentTimeMillis();
        // 分批
        List<List<T>> batches = Lists.partition(data, batchSize);
        // 任务
        List<Runnable> tasks = new ArrayList<>(batches.size());
        // 异常语句收集器
        ConcurrentHashMap<String, String> exceptionCollector = new ConcurrentHashMap<>(batches.size());
        // 批处理
        Connection connection = connectionGetter.get();
        connection.setAutoCommit(false);
        for (int i = 0; i < batches.size(); i++) {
            // 当前批次
            List<T> batch = batches.get(i);
            // 当前批次编号
            String batchId = String.valueOf(i);
            // 当前批次语句
            String batchSQL = NativeSQLBuilder.buildInsert(dialect, cls, batch);
            // 添加任务
            tasks.add(() -> {
                executor.execute(() -> {
                    long batchStart = System.currentTimeMillis();
                    String batchStatus;
                    try (Statement statement = connection.createStatement()) {
                        statement.execute(batchSQL);
                        batchStatus = "success";
                    } catch (Exception e) {
                        exceptionCollector.put(batchId, e.getMessage());
                        batchStatus = "failure";
                    }
                    long batchEnd = System.currentTimeMillis();
                    log.info("total={} batchSize={} batchCount={} batchId={} batchStatus={} batchCost={} batchSQL={}", data.size(), batchSize, batches.size(), batchId, batchStatus, batchEnd - batchStart, batchSQL);
                });
            });
        }
        // 执行任务
        AsyncAwait.awaitAll(tasks);
        // 结束时间
        long end = System.currentTimeMillis();
        // 提交任务
        return () -> {
            String txStatus;
            if (exceptionCollector.isEmpty()) {
                connection.commit();
                connection.close();
                txStatus = "commit";
            } else {
                connection.rollback();
                connection.close();
                txStatus = "rollback";
            }
            log.info("txStatus={} cost={}", txStatus, end - start);
            // 回滚时抛出异常以影响外部事务
            if (Objects.equals(txStatus, "rollback")) {
                throw new RuntimeException();
            }
        };
    }
}

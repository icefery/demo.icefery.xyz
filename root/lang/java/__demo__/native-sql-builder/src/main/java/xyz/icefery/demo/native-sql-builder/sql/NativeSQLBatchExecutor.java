package xyz.icefery.demo.util.sql;

import com.google.common.collect.Lists;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadPoolExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.icefery.demo.util.function.ThrowableConsumer;
import xyz.icefery.demo.util.function.ThrowableFunction;
import xyz.icefery.demo.util.function.ThrowableSupplier;
import xyz.icefery.demo.util.thread.AsyncAwait;

public class NativeSQLBatchExecutor {

    private static final Logger log = LoggerFactory.getLogger(NativeSQLBatchExecutor.class);

    public static <T> ThrowableConsumer<Boolean, Exception> batchInsert(
        ThreadPoolExecutor executor,
        ThrowableSupplier<Connection, Exception> connectionGetter,
        ThrowableFunction<List<T>, String, Exception> sqlGetter,
        List<T> data,
        int batchSize
    ) throws Exception {
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
            String batchSQL = sqlGetter.apply(batch);
            // 添加任务
            tasks.add(() -> {
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
                log.info(
                    "total={} batchSize={} batchCount={} batchId={} batchStatus={} batchCost={} batchSQL={}",
                    data.size(),
                    batchSize,
                    batches.size(),
                    batchId,
                    batchStatus,
                    batchEnd - batchStart,
                    batchSQL.replaceAll("\n", " ")
                );
            });
        }
        // 执行任务
        AsyncAwait.awaitAll(executor, tasks);
        // 结束时间
        long end = System.currentTimeMillis();
        // 提交任务
        return commitable -> {
            String txStatus;
            if (commitable && exceptionCollector.isEmpty()) {
                connection.commit();
                txStatus = "commit";
            } else {
                connection.rollback();
                txStatus = "rollback";
            }
            connection.close();
            log.info("txStatus={} cost={}", txStatus, end - start);
        };
    }
}

package xyz.icefery.demo.util;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.data.Stat;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;
import java.util.List;
import java.util.concurrent.CountDownLatch;

@Slf4j
public class ZooKeeperLock extends AbstractLock {
    // [path, depth]
    private static final ThreadLocal<Tuple2<String, Integer>> THREAD_LOCAL = new ThreadLocal<>();

    private final CuratorFramework curatorFramework;
    private final String name;
    private final String rootPath;
    private final String basePath;

    public ZooKeeperLock(CuratorFramework curatorFramework, String name) {
        this.curatorFramework = curatorFramework;
        this.name = name;
        this.rootPath = "/" + name;
        this.basePath = "/" + name + "/" + name + "-";
    }

    @SneakyThrows
    @Override
    public void lock() {
        Tuple2<String, Integer> tuple = THREAD_LOCAL.get();
        if (tuple != null && tuple.getT2() > 0) {
            tuple = Tuples.of(tuple.getT1(), tuple.getT2() + 1);
        } else {
            String path = curatorFramework.create().creatingParentContainersIfNeeded().withMode(CreateMode.EPHEMERAL_SEQUENTIAL).forPath(basePath);
            List<String> pathList = curatorFramework.getChildren().forPath(rootPath).stream().map(it -> rootPath + "/" + it).sorted().toList();
            int index = pathList.indexOf(path);
            if (index != 0) {
                CountDownLatch countDownLatch = new CountDownLatch(1);
                Watcher watcher = event -> countDownLatch.countDown();
                String prevPath = pathList.get(index - 1);
                Stat stat = curatorFramework.checkExists().usingWatcher(watcher).forPath(prevPath);
                if (stat != null) {
                    countDownLatch.await();
                }
            }
            tuple = Tuples.of(path, 1);
        }
        THREAD_LOCAL.set(tuple);
        log.info("[locked] thread={} depth={}", tuple.getT1(), tuple.getT2());
    }

    @SneakyThrows
    @Override
    public void unlock() {
        Tuple2<String, Integer> tuple = THREAD_LOCAL.get();
        if (tuple != null && tuple.getT2() > 0) {
            tuple = Tuples.of(tuple.getT1(), tuple.getT2() - 1);
            THREAD_LOCAL.set(tuple);
            if (tuple.getT2() == 0) {
                curatorFramework.delete().guaranteed().forPath(tuple.getT1());
                THREAD_LOCAL.remove();
            }
            log.info("[unlocked] thread={} depth={}", tuple.getT1(), tuple.getT2());
        }
    }
}

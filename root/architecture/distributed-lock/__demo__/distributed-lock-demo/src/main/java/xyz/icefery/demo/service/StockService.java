package xyz.icefery.demo.service;

import io.etcd.jetcd.Client;
import java.util.concurrent.locks.Lock;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;
import xyz.icefery.demo.dao.StockRepository;
import xyz.icefery.demo.util.AbstractLock;
import xyz.icefery.demo.util.EtcdLock;
import xyz.icefery.demo.util.RedisLock;
import xyz.icefery.demo.util.ZooKeeperLock;

@Slf4j
@RequiredArgsConstructor
@Service
public class StockService {

    private final StockRepository stockRepository;
    private final RedissonClient redissonClient;
    private final CuratorFramework curatorFramework;
    private final Client client;

    // redisson
    public Long deduct1() {
        Lock lock = redissonClient.getLock("lock1");
        lock.lock();
        try {
            System.out.println("deduct()");
            reenterant(lock);
            return stockRepository.deduct();
        } finally {
            lock.unlock();
        }
    }

    // custom redis
    public Long deduct2() {
        Lock lock = new RedisLock(redissonClient, "lock2");
        lock.lock();
        try {
            System.out.println("deduct()");
            reenterant(lock);
            return stockRepository.deduct();
        } finally {
            lock.unlock();
        }
    }

    // curator
    public Long deduct3() {
        Lock lock = new AbstractLock() {
            private final InterProcessMutex mutex = new InterProcessMutex(curatorFramework, "/lock3");

            @SneakyThrows
            @Override
            public void lock() {
                mutex.acquire();
            }

            @SneakyThrows
            @Override
            public void unlock() {
                mutex.release();
            }
        };

        lock.lock();
        try {
            System.out.println("deduct()");
            reenterant(lock);
            return stockRepository.deduct();
        } finally {
            lock.unlock();
        }
    }

    // zookeeper
    public Long deduct4() {
        Lock lock = new ZooKeeperLock(curatorFramework, "lock4");
        lock.lock();
        try {
            System.out.println("deduct()");
            reenterant(lock);
            return stockRepository.deduct();
        } finally {
            lock.unlock();
        }
    }

    // etcd
    public Long deduct5() {
        Lock lock = new EtcdLock(client, "lock6");
        lock.lock();
        try {
            System.out.println("deduct()");
            reenterant(lock);
            return stockRepository.deduct();
        } finally {
            lock.unlock();
        }
    }

    private void reenterant(Lock lock) {
        lock.lock();
        try {
            System.out.println("reenterant()");
        } finally {
            lock.unlock();
        }
    }
}

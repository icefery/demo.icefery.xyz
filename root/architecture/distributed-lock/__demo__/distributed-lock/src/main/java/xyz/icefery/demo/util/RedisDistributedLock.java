package xyz.icefery.demo.util;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class RedisDistributedLock implements Lock {
    private static final String DISTRIBUTED_ID = UUID.randomUUID().toString();

    private final RedisTemplate<String, String> redisTemplate;
    private final String lockName;
    private final String uuid;

    public RedisDistributedLock(RedisTemplate<String, String> redisTemplate, String lockName) {
        this.redisTemplate = redisTemplate;
        this.lockName = lockName;
        this.uuid = DISTRIBUTED_ID + ":" + Thread.currentThread().getId();
    }

    @Override
    public void lock() {
        tryLock();
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public boolean tryLock() {
        try {
            return tryLock(30L, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            return false;
        }
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        long expire = unit.toMillis(time);
        String script =
            """
            if redis.call('exists', KEYS[1]) == 0 or redis.call('hexists', KEYS[1], ARGV[1]) == 1 then
              redis.call('hincrby', KEYS[1], ARGV[1], 1)
              redis.call('expire', KEYS[1], ARGV[2])
              return 1
            else
              return 0
            end
            """;
        while (Objects.equals(redisTemplate.execute(new DefaultRedisScript<>(script, Boolean.class), List.of(lockName), uuid, String.valueOf(expire)), Boolean.FALSE)) {
            TimeUnit.MILLISECONDS.sleep(50);
        }
        return false;
    }

    @Override
    public void unlock() {
        String script =
            """
            if redis.call('hexists', KEYS[1], ARGV[1]) == 0 then
              return nil
            elseif redis.call('hincrby', KEYS[1], ARGV[1], -1) == 0 then
              return redis.call('del', KEYS[1])
            else
              return 0
            end
            """;
        redisTemplate.execute(new DefaultRedisScript<>(script, Boolean.class), List.of(lockName), uuid);
    }

    @Override
    public Condition newCondition() {
        return null;
    }
}

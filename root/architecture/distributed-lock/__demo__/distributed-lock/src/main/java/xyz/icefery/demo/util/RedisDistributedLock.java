package xyz.icefery.demo.util;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import java.util.List;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class RedisDistributedLock implements Lock {
    private static final ThreadLocal<String> THREAD_LOCAL = new ThreadLocal<>();
    private static final String DEFAULT_LOCK_NAME = "lock";
    private static final long DEFAULT_LOCK_EXPIRE = 30L;

    private static final String LUA_LOCK =
        """
        if redis.call('exists', KEYS[1]) == 0 or redis.call('hexists', KEYS[1], ARGV[1]) == 1 then
          redis.call('hincrby', KEYS[1], ARGV[1], 1)
          redis.call('expire', KEYS[1], ARGV[2])
          return 1
        else
          return 0
        end
        """;
    private static final String LUA_UNLOCK =
        """
        if redis.call('hexists', KEYS[1], ARGV[1]) == 0 then
          return nil
        elseif redis.call('hincrby', KEYS[1], ARGV[1], -1) == 0 then
          return redis.call('del', KEYS[1])
        else
          return 0
        end
        """;
    private static final String LUA_RENEWAL =
        """
        if redis.call('hexists', KEYS[1], ARGV[1]) == 1 then
          return redis.call('expire', KEYS[1], ARGV[2])
        else
          return 0
        end
        """;

    private final RedisTemplate<String, String> redisTemplate;
    private final String lockName;
    private final long lockExpire;
    private final Timer timer;

    public RedisDistributedLock(RedisTemplate<String, String> redisTemplate) {
        if (THREAD_LOCAL.get() == null) {
            String uuid = UUID.randomUUID().toString();
            THREAD_LOCAL.set(uuid);
        }
        this.redisTemplate = redisTemplate;
        this.lockName = DEFAULT_LOCK_NAME;
        this.lockExpire = DEFAULT_LOCK_EXPIRE;
        this.timer = new Timer();
    }

    @Override
    public void lock() {
        try {
            String uuid = THREAD_LOCAL.get();
            String expire = String.valueOf(lockExpire);
            // 获取锁
            RedisScript<Boolean> script = RedisScript.of(LUA_LOCK, Boolean.class);
            List<String> keys = List.of(lockName);
            Object[] args = new Object[]{uuid, expire};
            while (Objects.equals(redisTemplate.execute(script, keys, args), Boolean.FALSE)) {
                TimeUnit.MILLISECONDS.sleep(50);
            }
            // 自动续期
            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    RedisScript<Boolean> script = RedisScript.of(LUA_RENEWAL, Boolean.class);
                    List<String> keys = List.of(lockName);
                    Object[] args = new Object[]{uuid, expire};
                    redisTemplate.execute(script, keys, args);
                }
            };
            timer.schedule(task, 0, lockExpire * 1000 / 3);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public boolean tryLock() {
        return false;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public void unlock() {
        String uuid = THREAD_LOCAL.get();
        // 释放锁
        RedisScript<Boolean> script = RedisScript.of(LUA_UNLOCK, Boolean.class);
        List<String> keys = List.of(lockName);
        Object[] args = new Object[]{uuid};
        redisTemplate.execute(script, keys, args);
    }

    @Override
    public Condition newCondition() {
        return null;
    }
}

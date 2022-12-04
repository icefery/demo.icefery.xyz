package xyz.icefery.demo.util;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RScript;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.StringCodec;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
public class RedisLock extends AbstractLock {
    private static final ThreadLocal<String> THREAD_LOCAL = new ThreadLocal<>();

    // EVAL <lua> 1 <key> <uuid> <expire>
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
    // EVAL <lua> 1 <key> <uuid> <expire>
    private static final String LUA_RENEWAL =
        """
        if redis.call('hexists', KEYS[1], ARGV[1]) == 1 then
          return redis.call('expire', KEYS[1], ARGV[2])
        else
          return 0
        end
        """;
    // EVAL <lua> 1 <key> <uuid>
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


    private final RedissonClient redissonClient;
    private final String name;
    private final Long expire;
    private final ScheduledExecutorService scheduledExecutorService;


    public RedisLock(RedissonClient redissonClient, String name) {
        this.redissonClient = redissonClient;
        this.name = name;
        this.expire = 30L;
        this.scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        String uuid = THREAD_LOCAL.get();
        if (uuid == null) {
            uuid = UUID.randomUUID().toString();
            THREAD_LOCAL.set(uuid);
        }
    }


    @SneakyThrows
    @Override
    public synchronized void lock() {
        String uuid = THREAD_LOCAL.get();
        // 加锁
        while (Objects.equals(redissonClient.getScript(StringCodec.INSTANCE).eval(RScript.Mode.READ_WRITE, LUA_LOCK, RScript.ReturnType.BOOLEAN, List.of(name), uuid, expire), Boolean.FALSE)) {
            TimeUnit.MILLISECONDS.sleep(expire * 1000 / 1000);
        }
        // 自动续期
        scheduledExecutorService.scheduleAtFixedRate(
            () -> redissonClient.getScript(StringCodec.INSTANCE).eval(RScript.Mode.READ_WRITE, LUA_RENEWAL, RScript.ReturnType.BOOLEAN, List.of(name), uuid, expire),
            0,
            expire * 1000 / 3,
            TimeUnit.MILLISECONDS
        );
        log.info("[locked] thread={}", uuid);
    }


    @Override
    public synchronized void unlock() {
        String uuid = THREAD_LOCAL.get();
        redissonClient.getScript(StringCodec.INSTANCE).eval(RScript.Mode.READ_WRITE, LUA_UNLOCK, RScript.ReturnType.BOOLEAN, List.of(name), uuid);
        scheduledExecutorService.shutdownNow();
        log.info("[unlocked] thread={}", uuid);
    }
}

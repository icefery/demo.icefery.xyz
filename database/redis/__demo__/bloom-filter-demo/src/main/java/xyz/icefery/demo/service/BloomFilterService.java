package xyz.icefery.demo.service;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import xyz.icefery.demo.constant.AppConstant;
import xyz.icefery.demo.dao.UserMapper;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.function.Function;
import java.util.function.Supplier;

@Service
@Slf4j
public class BloomFilterService {
    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private UserMapper userMapper;

    private static <T> void rebuildBloomFilter(String filterName, Double filterFactor, Function<String, RBloomFilter<T>> filterGetter, Supplier<List<T>> listGetter) {
        long start = System.currentTimeMillis();

        List<T> list = listGetter.get();
        if (!list.isEmpty()) {
            RBloomFilter<T> filter = filterGetter.apply(filterName);
            filter.delete();
            filter.tryInit(list.size(), filterFactor);
            list.forEach(filter::add);
        }

        long end = System.currentTimeMillis();
        log.info("filterName={} filterFactor={} listSize={} rebuildCost={}", filterName, filterFactor, list.size(), end - start);
    }

    @Scheduled(cron = "0 0 0 ? * *")
    @Async
    public void rebuildBloomFilter() {
        Lock lock = redissonClient.getLock(AppConstant.LOCK__BLOOM_FILTER__REBUILD);
        if (lock.tryLock()) {
            try {
                rebuildBloomFilter(AppConstant.BLOOM_FILTER__USER__ID_LIST, AppConstant.BLOOM_FILTER__CONFIG__FALSE_PROBABILITY, redissonClient::getBloomFilter, userMapper::selectDistinctIdList);
            } finally {
                lock.unlock();
            }
        } else {
            log.warn("bloom filter already in rebuilding");
        }
    }
}

package xyz.icefery.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import xyz.icefery.demo.dao.StockRepository;
import xyz.icefery.demo.entity.StockEntity;
import xyz.icefery.demo.util.RedisDistributedLock;
import java.util.Optional;
import java.util.concurrent.locks.Lock;

@Service
public class StockService {
    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public void deduct() {
        Lock lock = new RedisDistributedLock(redisTemplate);
        lock.lock();
        try {
            reentrant();
            Optional<StockEntity> optional = stockRepository.findById(1L);
            optional.ifPresent(stock -> {
                if (stock.getStock() > 0) {
                    stock.setStock(stock.getStock() - 1);
                    stockRepository.save(stock);
                }
            });
        } finally {
            lock.unlock();
        }
    }

    public void reentrant() {
        Lock lock = new RedisDistributedLock(redisTemplate);
        lock.lock();
        System.out.println("reentrant()");
        lock.unlock();
    }
}

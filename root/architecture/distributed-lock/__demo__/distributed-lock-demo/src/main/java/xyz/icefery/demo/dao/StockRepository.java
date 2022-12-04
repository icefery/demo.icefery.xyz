package xyz.icefery.demo.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class StockRepository {
    private final RedisTemplate<String, String> redisTemplate;

    public Long deduct() {
        String key = "stock";
        String value = redisTemplate.opsForValue().get(key);
        Long stock = value == null ? 0L : Long.parseLong(value);
        if (stock > 0) {
            stock = stock - 1;
            value = String.valueOf(stock);
            redisTemplate.opsForValue().set(key, value);
        }
        return stock;
    }
}

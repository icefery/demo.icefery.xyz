package xyz.icefery.demo.business.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "stock-service")
public interface StockFeignClient {
    @GetMapping("/deduct")
    void deduct(@RequestParam("commodityId") Long commodityId, @RequestParam("count") Integer count);
}

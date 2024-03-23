package xyz.icefery.demo.business.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "order-service")
public interface OrderFeignClient {
    @GetMapping("/create")
    void create(
        @RequestParam("userId") Long userId,
        @RequestParam("commodityId") Long commodityId,
        @RequestParam("count") Integer count
    );
}

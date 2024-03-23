package xyz.icefery.demo.order.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.math.BigDecimal;

@FeignClient(name = "account-service")
public interface AccountFeignClient {
    @GetMapping("/debit")
    void debit(
        @RequestParam("userId") Long userId,
        @RequestParam("money") BigDecimal money
    );
}

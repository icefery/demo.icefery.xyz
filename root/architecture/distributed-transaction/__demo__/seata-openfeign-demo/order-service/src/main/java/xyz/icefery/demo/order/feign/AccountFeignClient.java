package xyz.icefery.demo.order.feign;

import java.math.BigDecimal;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "account-service")
public interface AccountFeignClient {
    @GetMapping("/debit")
    void debit(@RequestParam("userId") Long userId, @RequestParam("money") BigDecimal money);
}

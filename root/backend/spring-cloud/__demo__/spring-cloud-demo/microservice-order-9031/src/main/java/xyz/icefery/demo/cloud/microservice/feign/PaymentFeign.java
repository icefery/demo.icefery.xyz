package xyz.icefery.demo.cloud.microservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import xyz.icefery.demo.cloud.microservice.util.R;

@Service
@FeignClient(value = "MICROSERVICE-PAYMENT", fallback = PaymentFeign.Fallback.class)
public interface PaymentFeign {
    @GetMapping("/sleep/{milliseconds}")
    R<Object> sleep(@PathVariable("milliseconds") Long milliseconds);

    @Service
    class Fallback implements PaymentFeign {

        @Override
        public R<Object> sleep(Long milliseconds) {
            return R.failure(503, "[MICROSERVICE-PAYMENT] 暂时不可用");
        }
    }
}

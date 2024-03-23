package xyz.icefery.demo.cloud.microservice.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import xyz.icefery.demo.cloud.microservice.feign.PaymentFeign;
import xyz.icefery.demo.cloud.microservice.util.R;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
public class OrderController {
    @Value("${spring.application.name}")
    private String applicationName;

    @Value("${server.port}")
    private Integer port;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private PaymentFeign paymentFeign;

    @HystrixCommand(fallbackMethod = "wareFallback", commandProperties = {
        @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),
    })
    @GetMapping("/call_ware/sleep/{milliseconds}")
    public R<Object> callWare(@PathVariable Long milliseconds) {
        R ware = restTemplate.getForObject("http://MICROSERVICE-WARE/sleep/{milliseconds}", R.class, milliseconds);

        Map<String, Object> map = new HashMap<>();
        map.put("applicationName", applicationName);
        map.put("port", port);
        map.put("ware", ware);

        return R.success(map);
    }

    @GetMapping("/call_payment/sleep/{milliseconds}")
    public R<Object> callPayment(@PathVariable Long milliseconds) {
        R<Object> payment = paymentFeign.sleep(milliseconds);

        Map<String, Object> map = new HashMap<>();
        map.put("applicationName", applicationName);
        map.put("port", port);
        map.put("payment", payment);

        return R.success(map);
    }

    private R<Object> wareFallback(Long milliseconds) {
        R<Object> ware = R.failure(503, "[MICROSERVICE-WARE] 暂时不可用");

        Map<String, Object> map = new HashMap<>();
        map.put("applicationName", applicationName);
        map.put("port", port);
        map.put("ware", ware);

        return R.success(map);
    }
}
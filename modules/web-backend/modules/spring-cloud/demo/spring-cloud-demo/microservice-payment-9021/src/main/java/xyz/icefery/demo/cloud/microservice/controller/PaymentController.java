package xyz.icefery.demo.cloud.microservice.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import xyz.icefery.demo.cloud.microservice.util.R;

import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
public class PaymentController {
    @Value("${spring.application.name}")
    private String applicationName;

    @Value("${server.port}")
    private Integer port;

    @GetMapping("/sleep/{milliseconds}")
    public R<Map<String, Object>> sleep(@PathVariable Long milliseconds) {
        // @formatter:off
        try { TimeUnit.MILLISECONDS.sleep(milliseconds); } catch (InterruptedException e) { e.printStackTrace(); }
        long quotient = 1 / ((milliseconds + 1) % 2);
        // @formatter:on
        return R.success(Map.of("applicationName", this.applicationName, "port", port));
    }
}
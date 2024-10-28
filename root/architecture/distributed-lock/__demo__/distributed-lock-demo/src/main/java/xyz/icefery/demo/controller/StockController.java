package xyz.icefery.demo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.icefery.demo.service.StockService;

@RequiredArgsConstructor
@RestController
public class StockController {

    private final StockService stockService;

    // redisson lock
    @GetMapping("/stock/deduct1")
    public Long deduct1() {
        return stockService.deduct1();
    }

    // custom redis lock
    @GetMapping("/stock/deduct2")
    public Long deduct2() {
        return stockService.deduct2();
    }

    // curator lock
    @GetMapping("/stock/deduct3")
    public Long deduct3() {
        return stockService.deduct3();
    }

    // custom zookeeper lock
    @GetMapping("/stock/deduct4")
    public Long deduct4() {
        return stockService.deduct4();
    }

    // custom etcd lock
    @GetMapping("/stock/deduct5")
    public Long deduct5() {
        return stockService.deduct5();
    }
}

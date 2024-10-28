package xyz.icefery.demo.stock.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.icefery.demo.stock.service.StockService;

@RestController
public class StockController {

    @Autowired
    private StockService stockService;

    @GetMapping("/deduct")
    public void deduct(Long commodityId, Integer count) {
        stockService.deduct(commodityId, count);
    }
}

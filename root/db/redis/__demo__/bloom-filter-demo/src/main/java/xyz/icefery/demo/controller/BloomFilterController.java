package xyz.icefery.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.icefery.demo.service.BloomFilterService;
import xyz.icefery.demo.util.R;

@RestController
public class BloomFilterController {
    @Autowired
    private BloomFilterService bloomFilterService;

    @GetMapping("/bloom-filter/rebuild")
    public R<?> rebuild() {
        bloomFilterService.rebuildBloomFilter();
        return R.success();
    }
}

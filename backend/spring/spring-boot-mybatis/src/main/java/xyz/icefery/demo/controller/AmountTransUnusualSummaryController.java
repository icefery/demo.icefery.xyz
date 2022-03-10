package xyz.icefery.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.icefery.demo.entity.AmountTransUnusualSummaryDTO;
import xyz.icefery.demo.service.AmountTransUnusualSummaryService;
import java.util.List;

@RestController
public class AmountTransUnusualSummaryController {
    @Autowired
    private AmountTransUnusualSummaryService amountTransUnusualSummaryService;

    @GetMapping("/list")
    public List<AmountTransUnusualSummaryDTO> list(AmountTransUnusualSummaryDTO dto) {
        return amountTransUnusualSummaryService.list(dto);
    }
}
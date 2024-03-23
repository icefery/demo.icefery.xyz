package xyz.icefery.demo.business.controller;

import io.seata.core.exception.TransactionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.icefery.demo.business.service.BusinessService;

@RestController
public class BusinessController {
    @Autowired
    private BusinessService businessService;

    @GetMapping("/purchase/commit")
    public void purchaseCommit() throws TransactionException {
        businessService.purchase(1L, 1L, 1);
    }

    @GetMapping("/purchase/rollback")
    public void purchaseRollback() {
        try {
            businessService.purchase(2L, 1L, 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

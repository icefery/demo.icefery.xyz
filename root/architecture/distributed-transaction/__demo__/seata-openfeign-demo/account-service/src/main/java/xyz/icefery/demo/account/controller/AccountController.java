package xyz.icefery.demo.account.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.icefery.demo.account.service.AccountService;
import java.math.BigDecimal;

@RestController
public class AccountController {
    @Autowired
    private AccountService accountService;

    @GetMapping("/debit")
    public void debit(Long userId, BigDecimal money) {
        accountService.debit(userId, money);
    }
}

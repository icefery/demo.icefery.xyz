package xyz.icefery.demo.account.service;

import io.seata.core.context.RootContext;
import java.math.BigDecimal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.icefery.demo.account.dao.AccountMapper;
import xyz.icefery.demo.account.entity.Account;

@Slf4j
@Service
public class AccountService {

    @Autowired
    private AccountMapper accountMapper;

    @Transactional
    public void debit(Long userId, BigDecimal money) {
        log.info("xid={}", RootContext.getXID());

        Account account = accountMapper.selectById(userId);
        if (account == null) {
            throw new RuntimeException();
        }

        account.setMoney(account.getMoney().subtract(money));
        accountMapper.updateById(account);
    }
}

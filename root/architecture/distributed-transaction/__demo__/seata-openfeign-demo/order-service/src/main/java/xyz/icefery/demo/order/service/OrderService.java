package xyz.icefery.demo.order.service;

import io.seata.core.context.RootContext;
import java.math.BigDecimal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.icefery.demo.order.dao.OrderMapper;
import xyz.icefery.demo.order.entity.Order;
import xyz.icefery.demo.order.feign.AccountFeignClient;

@Slf4j
@Service
public class OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private AccountFeignClient accountFeignClient;

    @Transactional
    public void create(Long userId, Long commodityId, Integer count) {
        log.info("xid={}", RootContext.getXID());

        BigDecimal money = new BigDecimal("100");

        accountFeignClient.debit(userId, money);

        Order order = new Order();
        order.setUserId(userId);
        order.setCommodityId(commodityId);
        order.setCount(count);
        order.setMoney(money);

        orderMapper.insert(order);
    }
}

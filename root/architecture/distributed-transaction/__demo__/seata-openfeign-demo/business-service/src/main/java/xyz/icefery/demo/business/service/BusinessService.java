package xyz.icefery.demo.business.service;

import io.seata.core.context.RootContext;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.icefery.demo.business.feign.OrderFeignClient;
import xyz.icefery.demo.business.feign.StockFeignClient;

@Slf4j
@Service
public class BusinessService {

    @Autowired
    private StockFeignClient stockFeignClient;

    @Autowired
    private OrderFeignClient orderFeignClient;

    @GlobalTransactional
    public void purchase(Long userId, Long commodityId, Integer orderCount) {
        log.info("xid={}", RootContext.getXID());

        stockFeignClient.deduct(commodityId, orderCount);
        orderFeignClient.create(userId, commodityId, orderCount);
    }
}

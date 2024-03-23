package xyz.icefery.demo.stock.service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.seata.core.context.RootContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.icefery.demo.stock.dao.StockMapper;
import xyz.icefery.demo.stock.entity.Stock;

@Slf4j
@Service
public class StockService {
    @Autowired
    private StockMapper stockMapper;

    @Transactional
    public void deduct(Long commodityId, Integer count) {
        log.info("xid={}", RootContext.getXID());

        Stock stock = stockMapper.selectOne(Wrappers.<Stock>lambdaQuery().eq(Stock::getCommodityId, commodityId));
        if (stock == null) {
            throw new RuntimeException();
        }

        stock.setCount(stock.getCount() - count);
        stockMapper.updateById(stock);
    }
}

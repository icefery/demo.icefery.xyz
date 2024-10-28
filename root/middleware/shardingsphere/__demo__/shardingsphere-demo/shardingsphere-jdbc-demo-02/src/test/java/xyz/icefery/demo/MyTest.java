package xyz.icefery.demo;

import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import xyz.icefery.demo.dao.DictMapper;
import xyz.icefery.demo.dao.OrderItemMapper;
import xyz.icefery.demo.dao.OrderMapper;
import xyz.icefery.demo.entity.DictEntity;
import xyz.icefery.demo.entity.OrderEntity;
import xyz.icefery.demo.entity.OrderItemEntity;

@SpringBootTest
public class MyTest {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderItemMapper orderItemMapper;

    @Autowired
    private DictMapper dictMapper;

    @Test
    public void sharding__write() {
        for (long i = 1; i <= 10; i++) {
            String orderCode = "ORDER-" + i;

            OrderEntity order = new OrderEntity();
            order.setOrderCode(orderCode);
            orderMapper.insert(order);

            OrderItemEntity orderItem = new OrderItemEntity();
            orderItem.setOrderCode(orderCode);
            orderItem.setProductPrice(new BigDecimal("100.00"));
            orderItem.setProductCount(i);
            orderItemMapper.insert(orderItem);
        }
    }

    @Test
    public void sharding__read() {
        orderMapper.join();
    }

    @Test
    public void broadcast__write() {
        DictEntity dict = new DictEntity();
        dict.setDictKey("1");
        dict.setDictValue("49");
        dictMapper.insert(dict);
    }
}

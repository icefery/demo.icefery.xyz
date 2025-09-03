package xyz.icefery.demo.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import xyz.icefery.demo.entity.OrderEntity;

@Mapper
public interface OrderMapper extends BaseMapper<OrderEntity> {
    @Select(
        value = """
        SELECT a.order_code, SUM(b.product_price * b.product_count) AS amount
        FROM t_order a
        JOIN t_order_item b ON b.order_code = a.order_code
        GROUP BY a.order_code
        """
    )
    List<Map<String, BigDecimal>> join();
}

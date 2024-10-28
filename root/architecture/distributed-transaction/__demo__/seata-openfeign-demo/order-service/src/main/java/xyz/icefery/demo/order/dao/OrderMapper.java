package xyz.icefery.demo.order.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import xyz.icefery.demo.order.entity.Order;

@Mapper
public interface OrderMapper extends BaseMapper<Order> {}

package xyz.icefery.demo.stock.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import xyz.icefery.demo.stock.entity.Stock;

@Mapper
public interface StockMapper extends BaseMapper<Stock> {

}

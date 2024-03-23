package xyz.icefery.demo.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import xyz.icefery.demo.entity.DictEntity;

@Mapper
public interface DictMapper extends BaseMapper<DictEntity> {

}

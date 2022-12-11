package xyz.icefery.demo.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import xyz.icefery.demo.entity.User;

@Mapper
public interface UserMapper extends BaseMapper<User> {

}

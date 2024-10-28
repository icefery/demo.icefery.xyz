package xyz.icefery.demo.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import xyz.icefery.demo.entity.User;

@Mapper
public interface UserMapper extends BaseMapper<User> {
    @Select(value = "SELECT DISTINCT id FROM t_user")
    List<Long> selectDistinctIdList();

    @Select(value = "SELECT DISTINCT username FROM t_user")
    List<String> selectDistinctUsernameList();

    @Select(value = "SELECT DISTINCT email FROM t_user")
    List<String> selectDistinctEmailList();
}

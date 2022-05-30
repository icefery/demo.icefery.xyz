package xyz.icefery.demo.ssm.mapper;

import org.apache.ibatis.annotations.Mapper;
import xyz.icefery.demo.ssm.entity.User;

@Mapper
public interface UserMapper {
    User selectById(Long id);

    void updateById(User user);
}
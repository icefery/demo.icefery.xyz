package xyz.icefery.demo.ssm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.icefery.demo.ssm.entity.User;
import xyz.icefery.demo.ssm.mapper.UserMapper;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public User selectUserById(Long id) {
        return userMapper.selectById(id);
    }

    @Transactional
    public void updateUserById(User user) {
        userMapper.updateById(user);
        //
        int a = 1 / 0;
    }
}

package xyz.icefery.demo;

import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import xyz.icefery.demo.dao.UserMapper;
import xyz.icefery.demo.dao.UserRepository;
import xyz.icefery.demo.entity.User;

@SpringBootTest
public class MyTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    // JPA | 只写
    @Test
    @Transactional
    public void jpa__write() {
        for (int i = 0; i < 5; i++) {
            User user = new User();
            user.setUsername(UUID.randomUUID().toString());
            userRepository.save(user);
        }
    }

    // JPA | 只读
    @Test
    public void jpa__read() {
        for (int i = 0; i < 5; i++) {
            List<User> list = userRepository.findAll();
        }
    }

    // JPA | 只读事务 | 只读
    @Test
    @Transactional(readOnly = true)
    public void jpa__read__with_readonly_transaction() {
        for (int i = 0; i < 5; i++) {
            List<User> list = userRepository.findAll();
        }
    }

    // JPA | 显示事务 | 读写
    @Test
    @Transactional
    public void jpa__read__write() {
        for (int i = 0; i < 5; i++) {
            User user = new User();
            user.setUsername(UUID.randomUUID().toString());
            userRepository.save(user);
            List<User> list = userRepository.findAll();
        }
    }

    // MyBatis | 只写
    @Test
    public void mybatis__write() {
        for (int i = 0; i < 5; i++) {
            User user = new User();
            user.setUsername(UUID.randomUUID().toString());
            userMapper.insert(user);
        }
    }

    // MyBatis | 只读
    @Test
    public void mybatis__read() {
        for (int i = 0; i < 5; i++) {
            List<User> list = userMapper.selectList(null);
        }
    }

    // MyBatis | 无显式事务 | 读写
    @Test
    public void mybatis__read__write__with_no_transaction() {
        for (int i = 0; i < 5; i++) {
            User user = new User();
            user.setUsername(UUID.randomUUID().toString());
            userMapper.insert(user);
            List<User> list = userMapper.selectList(null);
        }
    }

    // MyBatis | 显示事务 | 读写
    @Transactional
    @Test
    public void mybatis__read__write__with_transaction() {
        for (int i = 0; i < 5; i++) {
            User user = new User();
            user.setUsername(UUID.randomUUID().toString());
            userMapper.insert(user);
            List<User> list = userMapper.selectList(null);
        }
    }
}

package xyz.icefery.demo.service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.icefery.demo.constant.AppConstant;
import xyz.icefery.demo.dao.UserMapper;
import xyz.icefery.demo.entity.User;
import java.util.List;

@Service
@Slf4j
public class UserService {
    @Autowired
    private UserMapper userMapper;

    @CacheEvict(cacheNames = AppConstant.CACHE__USER__EMAIL, allEntries = true, beforeInvocation = true)
    @Transactional
    public User createUser(User user) {
        boolean duplicate = userMapper.exists(Wrappers.<User>lambdaQuery().eq(User::getUsername, user.getUsername()));
        if (duplicate) {
            throw new RuntimeException(String.format("user[username=%s] already exists", user.getUsername()));
        }
        userMapper.insert(user);
        return user;
    }

    @Cacheable(cacheNames = AppConstant.CACHE__USER__ID, key = "#id")
    public User findUserById(Long id) {
        return userMapper.selectById(id);
    }

    @Cacheable(cacheNames = AppConstant.CACHE__USER__USERNAME, key = "#username")
    public User findUserByUsername(String username) {
        return userMapper.selectOne(Wrappers.<User>lambdaQuery().eq(User::getUsername, username));
    }

    @Cacheable(cacheNames = AppConstant.CACHE__USER__EMAIL, key = "#email")
    public List<User> findUserListByEmail(String email) {
        return userMapper.selectList(Wrappers.<User>lambdaQuery().eq(User::getEmail, email));
    }

    @Caching(evict = {
        @CacheEvict(cacheNames = AppConstant.CACHE__USER__ID, key = "#user.id", beforeInvocation = true),
        @CacheEvict(cacheNames = AppConstant.CACHE__USER__USERNAME, allEntries = true, beforeInvocation = true),
        @CacheEvict(cacheNames = AppConstant.CACHE__USER__EMAIL, allEntries = true, beforeInvocation = true)
    })
    @Transactional
    public User updateUser(User user) {
        boolean exists = userMapper.exists(Wrappers.<User>lambdaQuery().eq(User::getId, user.getId()));
        if (!exists) {
            throw new RuntimeException(String.format("user[id=%s] not exists", user.getId()));
        }
        boolean duplicate = user.getUsername() != null && userMapper.exists(Wrappers.<User>lambdaQuery().eq(User::getUsername, user.getUsername()));
        if (duplicate) {
            throw new RuntimeException(String.format("user[username=%s] already exists", user.getUsername()));
        }
        userMapper.updateById(user);
        return user;
    }

    @Caching(evict = {
        @CacheEvict(cacheNames = AppConstant.CACHE__USER__ID, key = "#id", beforeInvocation = true),
        @CacheEvict(cacheNames = AppConstant.CACHE__USER__USERNAME, allEntries = true, beforeInvocation = true),
        @CacheEvict(cacheNames = AppConstant.CACHE__USER__EMAIL, allEntries = true, beforeInvocation = true)
    })
    @Transactional
    public void deleteUser(Long id) {
        boolean exists = userMapper.exists(Wrappers.<User>lambdaQuery().eq(User::getId, id));
        if (!exists) {
            throw new RuntimeException(String.format("user[id=%s] not exists", id));
        }
        userMapper.deleteById(id);
    }
}

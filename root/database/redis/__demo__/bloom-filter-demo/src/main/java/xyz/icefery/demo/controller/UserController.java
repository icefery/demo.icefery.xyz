package xyz.icefery.demo.controller;

import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import xyz.icefery.demo.constant.AppConstant;
import xyz.icefery.demo.entity.User;
import xyz.icefery.demo.service.UserService;
import xyz.icefery.demo.util.R;
import java.util.Collections;
import java.util.List;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private RedissonClient redissonClient;

    @PostMapping("/user/create")
    public R<?> create(@RequestBody User user) {
        User data = userService.createUser(user);
        redissonClient.getBloomFilter(AppConstant.BLOOM_FILTER__USER__ID_LIST).add(data.getId());
        return R.success(data);
    }

    @GetMapping("/user/find-list")
    public R<?> findList(@RequestParam(required = false) String email) {
        List<User> data = Collections.emptyList();
        if (email != null) {
            data = userService.findUserListByEmail(email);
        }
        return R.success(data);
    }

    @GetMapping("/user/find")
    public R<?> find(@RequestParam(required = false) Long id, @RequestParam(required = false) String username) {
        User data = null;
        if (id != null) {
            boolean contains = redissonClient.getBloomFilter(AppConstant.BLOOM_FILTER__USER__ID_LIST).contains(id);
            data = !contains ? null : userService.findUserById(id);
        } else if (username != null) {
            data = userService.findUserByUsername(username);
        }
        return R.success(data);
    }

    @PutMapping("/user/update")
    public R<?> update(@RequestBody User user) {
        User data = userService.updateUser(user);
        redissonClient.getBloomFilter(AppConstant.BLOOM_FILTER__USER__ID_LIST).add(data.getId());
        return R.success(data);
    }

    @DeleteMapping("/user/delete")
    public R<?> delete(Long id) {
        userService.deleteUser(id);
        return R.success();
    }
}

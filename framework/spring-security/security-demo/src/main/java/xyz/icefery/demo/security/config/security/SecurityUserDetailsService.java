package xyz.icefery.demo.security.config.security;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import xyz.icefery.demo.security.entity.Role;
import xyz.icefery.demo.security.entity.User;
import xyz.icefery.demo.security.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class SecurityUserDetailsService implements UserDetailsService {
    @Autowired private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User one = userService.getOne(new LambdaQueryWrapper<User>().eq(User::getUsername, username));
        if (one == null) {

            log.info("用户 [" + username + "] 不存在");

            throw new UsernameNotFoundException("用户 [" + username + "] 不存在");
        }
        List<Role> roleList = userService.getRoleListById(one.getId());

        log.info("用户 [" + username + "] 的角色有" + roleList.stream().map(Role::getName).collect(Collectors.toList()));

        return new SecurityUserDetails(one, roleList);
    }
}

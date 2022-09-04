package xyz.icefery.demo.security.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.icefery.demo.security.entity.Role;
import xyz.icefery.demo.security.entity.RoleUser;
import xyz.icefery.demo.security.entity.User;
import xyz.icefery.demo.security.mapper.UserMapper;
import xyz.icefery.demo.security.service.RoleService;
import xyz.icefery.demo.security.service.RoleUserService;
import xyz.icefery.demo.security.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author icefery
 * @since 2020-07-19
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired private RoleUserService roleUserService;
    @Autowired private RoleService     roleService;

    @Override
    public List<Role> getRoleListById(Long id) {
        List<RoleUser> roleUserList = roleUserService.list(new LambdaQueryWrapper<RoleUser>().eq(RoleUser::getUserId, id));
        return roleUserList
                .stream()
                .map(roleUser -> roleService.getById(roleUser.getRoleId()))
                .collect(Collectors.toList());
    }

}

package xyz.icefery.demo.security.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.icefery.demo.security.entity.Permission;
import xyz.icefery.demo.security.entity.Role;
import xyz.icefery.demo.security.entity.RolePermission;
import xyz.icefery.demo.security.mapper.PermissionMapper;
import xyz.icefery.demo.security.service.PermissionService;
import xyz.icefery.demo.security.service.RolePermissionService;
import xyz.icefery.demo.security.service.RoleService;

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
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {
    @Autowired private RolePermissionService rolePermissionService;
    @Autowired private RoleService           roleService;

    @Override
    public List<Role> getRoleListById(Long id) {
        List<RolePermission> rolePermissionList =
                rolePermissionService.list(new LambdaQueryWrapper<RolePermission>().eq(RolePermission::getPermissionId, id));
        return rolePermissionList
                .stream()
                .map(rolePermission -> roleService.getById(rolePermission.getRoleId()))
                .collect(Collectors.toList());
    }
}

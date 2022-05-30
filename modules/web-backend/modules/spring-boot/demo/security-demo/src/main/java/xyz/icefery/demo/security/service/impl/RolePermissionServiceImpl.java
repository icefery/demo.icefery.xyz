package xyz.icefery.demo.security.service.impl;

import xyz.icefery.demo.security.entity.RolePermission;
import xyz.icefery.demo.security.mapper.RolePermissionMapper;
import xyz.icefery.demo.security.service.RolePermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author icefery
 * @since 2020-07-19
 */
@Service
public class RolePermissionServiceImpl extends ServiceImpl<RolePermissionMapper, RolePermission> implements RolePermissionService {

}

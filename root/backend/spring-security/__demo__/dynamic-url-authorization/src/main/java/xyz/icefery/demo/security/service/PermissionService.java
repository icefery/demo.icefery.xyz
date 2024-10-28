package xyz.icefery.demo.security.service;

import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;
import xyz.icefery.demo.security.entity.Permission;
import xyz.icefery.demo.security.entity.Role;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author icefery
 * @since 2020-07-19
 */
public interface PermissionService extends IService<Permission> {
    List<Role> getRoleListById(Long id);
}

package xyz.icefery.demo.security.service;

import xyz.icefery.demo.security.entity.Role;
import xyz.icefery.demo.security.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author icefery
 * @since 2020-07-19
 */
public interface UserService extends IService<User> {

    List<Role> getRoleListById(Long id);
}

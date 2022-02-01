package xyz.icefery.demo.security.service.impl;

import xyz.icefery.demo.security.entity.Role;
import xyz.icefery.demo.security.mapper.RoleMapper;
import xyz.icefery.demo.security.service.RoleService;
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
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

}

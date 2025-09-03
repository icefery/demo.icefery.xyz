package xyz.icefery.demo.security.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import xyz.icefery.demo.security.entity.RoleUser;
import xyz.icefery.demo.security.mapper.RoleUserMapper;
import xyz.icefery.demo.security.service.RoleUserService;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author icefery
 * @since 2020-07-19
 */
@Service
public class RoleUserServiceImpl extends ServiceImpl<RoleUserMapper, RoleUser> implements RoleUserService {}

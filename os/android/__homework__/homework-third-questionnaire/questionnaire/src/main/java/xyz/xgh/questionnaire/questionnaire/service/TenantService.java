package xyz.xgh.questionnaire.questionnaire.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.xgh.questionnaire.questionnaire.entity.Tenant;
import xyz.xgh.questionnaire.questionnaire.exception.CustomException;
import xyz.xgh.questionnaire.questionnaire.mapper.TenantMapper;

@Service
public class TenantService {
    @Autowired
    private TenantMapper tenantMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Tenant findTenantByUsername(String username) {
        LambdaQueryWrapper<Tenant> wrapper = new LambdaQueryWrapper<Tenant>().eq(Tenant::getUsername, username);
        return tenantMapper.selectOne(wrapper);
    }

    @Transactional
    public String registerTenant(Tenant tenant) {
        String username = tenant.getUsername();
        Tenant find = this.findTenantByUsername(username);
        if (find != null) {
            throw new CustomException("账号已存在 " + username);
        }
        String rawPassword = tenant.getPassword();
        String encodedPassword = passwordEncoder.encode(rawPassword);
        tenant.setPassword(encodedPassword);
        tenantMapper.insert(tenant);
        return tenant.getId();
    }
}

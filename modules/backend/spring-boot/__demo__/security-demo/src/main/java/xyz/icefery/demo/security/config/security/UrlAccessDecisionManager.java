package xyz.icefery.demo.security.config.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Slf4j
@Component
public class UrlAccessDecisionManager implements AccessDecisionManager {
    @Override
    public void decide(Authentication authentication, Object o, Collection<ConfigAttribute> collection) throws AccessDeniedException, InsufficientAuthenticationException {
        for (ConfigAttribute configAttribute : collection) {
            String needRoleName = configAttribute.getAttribute();

            if ("ROLE_LOGIN".equals(needRoleName)) {
                if (authentication instanceof AnonymousAuthenticationToken) {
                    log.info("资源需要登录访问 | 当前未登录");
                    throw new InsufficientAuthenticationException("未登录");
                } else if (authentication instanceof UsernamePasswordAuthenticationToken) {
                    log.info("资源需要登录访问 | 当前已登录 | 正常访问");
                    return;
                }
            }

            for (GrantedAuthority authority : authentication.getAuthorities()) {

                log.info("资源需要角色 [" + needRoleName + "] 用户拥有角色 [" + authority.getAuthority() + "]");

                if (authority.getAuthority().equals(needRoleName)) {
                    return;
                }
            }
        }

        log.info("当前登录用户无访问资源所需角色");

        throw new AccessDeniedException("权限不足");
    }

    @Override
    public boolean supports(ConfigAttribute configAttribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}

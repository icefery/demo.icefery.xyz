package xyz.icefery.demo.security.config.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import xyz.icefery.demo.security.entity.Permission;
import xyz.icefery.demo.security.entity.Role;
import xyz.icefery.demo.security.service.PermissionService;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Slf4j
@Component
public class UrlFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
    @Autowired private AntPathMatcher    antPathMatcher;
    @Autowired private PermissionService permissionService;

    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        String requestUrl = ((FilterInvocation) object).getRequestUrl();

        log.info("请求 [" + requestUrl + "]");

        // 匹配 url | 查找角色
        List<Permission> permissionList = permissionService.list();

        // TODO: 排序 url 使其能优先比较更细粒度的 url

        for (Permission p : permissionList) {
            if (antPathMatcher.match(p.getUrl(), requestUrl)) {
                if (p.getAnonymous()) {

                    log.info("[" + requestUrl + "] 允许匿名访问");

                    // 返回 null 不会进入 UrlAccessDecisionManager#decide 方法 | 直接放行
                    // 如果 authentication instanceof AnonymousAuthenticationToken | 即"用户"是匿名访问时用户拥有的角色
                    // 那么"用户"拥有的角色集合 authentication.getAuthorities() 里的角色为 ROLE_ANONYMOUS
                    // 因此也可返回资源需要匿名访问的角色为 ROLE_ANONYMOUS | return SecurityConfig.createList("ROLE_ANONYMOUS")
                    // 然后在 UrlAccessDecisionManager#decide 方法里进行判断 | 如果 "ROLE_ANONYMOUS".equals(needRoleName) 则 return 放行
                    return null;
                }

                List<Role> roleList = permissionService.getRoleListById(p.getId());

                // ROLE_LOGIN 是自定义的需要登录访问的角色
                if (roleList.isEmpty()) {
                    log.info("资源 [" + p.getUrl() + "] 无需角色 | 登录即可访问");

                    return SecurityConfig.createList("ROLE_LOGIN");
                }

                String[] roleNames = roleList.stream().map(Role::getName).toArray(String[]::new);

                log.info("资源 [" + p.getUrl() + "] 需要角色 " + Arrays.toString(roleNames));

                return SecurityConfig.createList(roleNames);
            }
        }

        log.info("请求 [" + requestUrl + "] 未在资源表中, 登录即可访问");

        // ROLE_LOGIN 是自定义的需要登录访问的角色
        return SecurityConfig.createList("ROLE_LOGIN");
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return FilterInvocation.class.isAssignableFrom(aClass);
    }

    @Bean
    public AntPathMatcher antPathMatcher() {
        return new AntPathMatcher();
    }
}

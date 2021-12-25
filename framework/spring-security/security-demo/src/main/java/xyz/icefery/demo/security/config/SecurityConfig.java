package xyz.icefery.demo.security.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.*;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import xyz.icefery.demo.security.config.security.JwtAuthenticationFilter;
import xyz.icefery.demo.security.config.security.SecurityUserDetailsService;
import xyz.icefery.demo.security.config.security.UrlAccessDecisionManager;
import xyz.icefery.demo.security.config.security.UrlFilterInvocationSecurityMetadataSource;

@Slf4j
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired private JwtConfig                  jwtConfig;
    @Autowired private JwtAuthenticationFilter    jwtAuthenticationFilter;
    @Autowired private SecurityUserDetailsService securityUserDetailsService;
    @Autowired private UrlFilterInvocationSecurityMetadataSource urlFilterInvocationSecurityMetadataSource;
    @Autowired private UrlAccessDecisionManager                  urlAccessDecisionManager;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 关闭 csrf
        http.csrf().disable();

        // 开启跨域
        http.cors();

        http.authorizeRequests()
            // 使用 URL 权限验证
            .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                @Override
                public <O extends FilterSecurityInterceptor> O postProcess(O object) {
                    object.setSecurityMetadataSource(urlFilterInvocationSecurityMetadataSource);
                    object.setAccessDecisionManager(urlAccessDecisionManager);
                    return object;
                }
            })
            .anyRequest().authenticated();

        // 表单登录
        http.formLogin()
            .loginProcessingUrl("/login")
            // 登录成功
            .successHandler((request, response, authentication) -> {
                UserDetails userDetails = (UserDetails) authentication.getPrincipal();
                String token = jwtConfig.createToken(userDetails.getUsername());
                response.setHeader(jwtConfig.getTokenHeader(), token);
                response.setCharacterEncoding("UTF-8");
                response.setContentType("text/plain;charset=utf-8");
                response.getWriter().write("登录成功");
            })
            // 登录失败
            .failureHandler((request, response, authenticationException) -> {
                String reason;
                if (authenticationException instanceof UsernameNotFoundException || authenticationException instanceof BadCredentialsException) {
                    reason = "用户名不存在或密码错误";
                } else if (authenticationException instanceof AccountExpiredException) {
                    reason = "账号过期";
                } else if (authenticationException instanceof CredentialsExpiredException) {
                    reason = "密码过期";
                } else if (authenticationException instanceof LockedException) {
                    reason = "账号锁定";
                } else if (authenticationException instanceof DisabledException) {
                    reason = "账号禁用";
                } else {
                    reason = "其它原因";
                }
                response.setCharacterEncoding("UTF-8");
                response.setContentType("text/plain;charset=utf-8");
                response.getWriter().write(reason);
            });

        http.exceptionHandling()
            // 登录过期 | 未登录
            .authenticationEntryPoint((request, response, authenticationException) -> {
                response.setCharacterEncoding("UTF-8");
                response.setContentType("text/plain;charset=utf-8");
                response.getWriter().write("登录过期 | 未登录");
            })
            // 权限不足
            .accessDeniedHandler((request, response, accessdeniedException) -> {
                response.setCharacterEncoding("UTF-8");
                response.setContentType("text/plain;charset=utf-8");
                response.getWriter().write("权限不足");
            });

        // JWT 过滤器
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        // 禁用 session
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(securityUserDetailsService).passwordEncoder(passwordEncoder());
    }

}

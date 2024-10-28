package xyz.xgh.questionnaire.questionnaire.config;

import io.jsonwebtoken.ExpiredJwtException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;
import xyz.xgh.questionnaire.questionnaire.entity.Tenant;
import xyz.xgh.questionnaire.questionnaire.service.TenantService;
import xyz.xgh.questionnaire.questionnaire.util.HttpServletUtil;
import xyz.xgh.questionnaire.questionnaire.util.JwtUtil;
import xyz.xgh.questionnaire.questionnaire.util.R;

@Slf4j
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private TenantService tenantService;

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/doc.html", "/swagger-ui.html", "/v2/**", "/swagger-resources/**", "/webjars/**");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public AuthenticationSuccessHandler successHandler() {
        return (request, response, authentication) -> {
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            Tenant find = tenantService.findTenantByUsername(username);
            String jwt = JwtUtil.createJws(username, password);
            // TODO dto with success response
            // response.setHeader(JwtUtil.HEADER, jwt);
            Map<String, Object> map = new HashMap<>();
            map.put("jwt", jwt);
            map.put("tenant", find);
            R<?> r = R.success(map);
            HttpServletUtil.responseJson(response, r);
        };
    }

    // 认证失败
    public AuthenticationFailureHandler failureHandler() {
        return (request, response, exception) -> {
            R<?> r;
            if (exception instanceof UsernameNotFoundException || exception instanceof BadCredentialsException) {
                r = R.failure(R.Code.LOGIN_FAILURE_USERNAME_NOT_FOUND_OR_BAD_CREDENTIALS);
            } else {
                r = R.failure(R.Code.LOGIN_FAILURE);
            }
            HttpServletUtil.responseJson(response, r);
        };
    }

    // 权限不足
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return (request, response, authException) -> {
            R<?> r = R.failure(R.Code.NOT_LOGGED_IN);
            HttpServletUtil.responseJson(response, r);
        };
    }

    public AccessDeniedHandler accessDeniedHandler() {
        return (request, response, accessDeniedException) -> {
            R<?> r = R.failure(R.Code.PERMISSION_DENIED, accessDeniedException.getMessage());
            HttpServletUtil.responseJson(response, r);
        };
    }

    public OncePerRequestFilter jwtTokenFilter() {
        return new OncePerRequestFilter() {
            @Override
            protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
                throws ServletException, IOException {
                String jwt = request.getHeader(JwtUtil.HEADER);
                boolean responsed = false;
                log.info("[jwtTokenFilter] jwt = " + jwt);
                if (StringUtils.isNotBlank(jwt)) {
                    String username = "";
                    String password = "";
                    try {
                        String[] arr = JwtUtil.parseJws(jwt);
                        username = arr[0];
                        password = arr[1];
                    } catch (ExpiredJwtException e) {
                        R<?> r = R.failure(R.Code.TOKEN_EXPIRED);
                        HttpServletUtil.responseJson(response, r);
                        responsed = true;
                    } catch (Exception e) {
                        R<?> r = R.failure(R.Code.TOKEN_INVALID);
                        HttpServletUtil.responseJson(response, r);
                        responsed = true;
                    }
                    log.info("[jwtTokenFilter] username = " + username + " password = " + password);
                    if (StringUtils.isNoneBlank(username, password)) {
                        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password, AuthorityUtils.NO_AUTHORITIES);
                        SecurityContextHolder.getContext().setAuthentication(token);
                    }
                }
                if (!responsed) {
                    filterChain.doFilter(request, response);
                }
            }
        };
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // cors
        http = http
            .cors()
            .and()
            // disable csrf
            .csrf()
            .disable()
            // disable session
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and();

        http = http
            .authorizeRequests()
            .antMatchers("/tenant/login", "/tenant/register", "/**/questionnaire/find/id/**", "/**/questionnaire-item/create")
            .permitAll()
            .anyRequest()
            .authenticated()
            .and();

        http = http.formLogin().loginProcessingUrl("/tenant/login").permitAll().successHandler(successHandler()).failureHandler(failureHandler()).and();

        http = http.exceptionHandling().authenticationEntryPoint(authenticationEntryPoint()).accessDeniedHandler(accessDeniedHandler()).and();

        http = http.addFilterBefore(jwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        UserDetailsService userDetailsService = username -> {
            if (StringUtils.isBlank(username)) {
                throw new UsernameNotFoundException(username);
            }
            Tenant find = tenantService.findTenantByUsername(username);
            if (find == null) {
                throw new UsernameNotFoundException(username);
            }
            return new User(username, find.getPassword(), AuthorityUtils.NO_AUTHORITIES);
        };
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }
}

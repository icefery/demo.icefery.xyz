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
        // ?????? csrf
        http.csrf().disable();

        // ????????????
        http.cors();

        http.authorizeRequests()
            // ?????? URL ????????????
            .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                @Override
                public <O extends FilterSecurityInterceptor> O postProcess(O object) {
                    object.setSecurityMetadataSource(urlFilterInvocationSecurityMetadataSource);
                    object.setAccessDecisionManager(urlAccessDecisionManager);
                    return object;
                }
            })
            .anyRequest().authenticated();

        // ????????????
        http.formLogin()
            .loginProcessingUrl("/login")
            // ????????????
            .successHandler((request, response, authentication) -> {
                UserDetails userDetails = (UserDetails) authentication.getPrincipal();
                String token = jwtConfig.createToken(userDetails.getUsername());
                response.setHeader(jwtConfig.getTokenHeader(), token);
                response.setCharacterEncoding("UTF-8");
                response.setContentType("text/plain;charset=utf-8");
                response.getWriter().write("????????????");
            })
            // ????????????
            .failureHandler((request, response, authenticationException) -> {
                String reason;
                if (authenticationException instanceof UsernameNotFoundException || authenticationException instanceof BadCredentialsException) {
                    reason = "?????????????????????????????????";
                } else if (authenticationException instanceof AccountExpiredException) {
                    reason = "????????????";
                } else if (authenticationException instanceof CredentialsExpiredException) {
                    reason = "????????????";
                } else if (authenticationException instanceof LockedException) {
                    reason = "????????????";
                } else if (authenticationException instanceof DisabledException) {
                    reason = "????????????";
                } else {
                    reason = "????????????";
                }
                response.setCharacterEncoding("UTF-8");
                response.setContentType("text/plain;charset=utf-8");
                response.getWriter().write(reason);
            });

        http.exceptionHandling()
            // ???????????? | ?????????
            .authenticationEntryPoint((request, response, authenticationException) -> {
                response.setCharacterEncoding("UTF-8");
                response.setContentType("text/plain;charset=utf-8");
                response.getWriter().write("???????????? | ?????????");
            })
            // ????????????
            .accessDeniedHandler((request, response, accessdeniedException) -> {
                response.setCharacterEncoding("UTF-8");
                response.setContentType("text/plain;charset=utf-8");
                response.getWriter().write("????????????");
            });

        // JWT ?????????
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        // ?????? session
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

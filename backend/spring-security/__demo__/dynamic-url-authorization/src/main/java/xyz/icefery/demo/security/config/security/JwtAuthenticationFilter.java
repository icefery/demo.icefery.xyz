package xyz.icefery.demo.security.config.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import xyz.icefery.demo.security.config.JwtConfig;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// Token 过滤器
@Slf4j
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private JwtConfig jwtConfig;
    @Autowired
    private SecurityUserDetailsService securityUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        // 从请求头中拿到 token
        String token = request.getHeader(jwtConfig.getTokenHeader());
        if (token != null && !token.isBlank()) {
            String username = null;
            try {
                username = jwtConfig.extractUsername(token);
            } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | SignatureException | IllegalArgumentException e) {

                log.info("Token 过期或有误");

            }
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                // 检验 token
                boolean valid = jwtConfig.validateToken(token, username);
                if (valid) {
                    // 加载登录信息(用户名 | 密码 | 角色)
                    UserDetails userDetails = securityUserDetailsService.loadUserByUsername(username);
                    UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }
        chain.doFilter(request, response);
    }
}

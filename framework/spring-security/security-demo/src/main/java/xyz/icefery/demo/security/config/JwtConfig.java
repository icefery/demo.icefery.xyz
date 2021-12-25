package xyz.icefery.demo.security.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.Date;

@Data
@Component
@PropertySource("classpath:jwt.properties")
@ConfigurationProperties(prefix = "jwt")
public class JwtConfig {
    // 密钥
    private String secret;
    // 过期时间
    private Long   expiration;
    // 请求头
    private String tokenHeader;

    // 生成 Token
    public String createToken(String username) {
        long now = System.currentTimeMillis();
        return Jwts.builder()
                   .setSubject(username)
                   .setIssuedAt(new Date(now))
                   .setExpiration(new Date(now + expiration))
                   .signWith(SignatureAlgorithm.HS256, secret)
                   .compact();
    }

    // 校验 Token
    public Boolean validateToken(String token, String username) {
        Claims claims = this.extractClaims(token);
        boolean isUsernameCorrect = claims.getSubject().equals(username);
        boolean isTokenExpired = claims.getExpiration().before(new Date());
        return isUsernameCorrect && !isTokenExpired;
    }

    public Claims extractClaims(String token) {
        return Jwts.parser()
                   .setSigningKey(secret)
                   .parseClaimsJws(token)
                   .getBody();
    }

    // 从 Token 中提取用户名
    public String extractUsername(String token) {
        return this.extractClaims(token).getSubject();
    }
}

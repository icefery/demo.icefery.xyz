package xyz.xgh.questionnaire.questionnaire.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;

public class JwtUtil {
    public static final String HEADER = "Authorization";
    public static final String SECRET_KEY = "SECRET_KEY";
    public static final Integer EXPIRE_TIME = 1000 * 60 * 60;

    public static String createJws(String username, String password) {
        return Jwts.builder()
                   .setSubject(username)
                   .claim("password", password)
                   .setExpiration(new Date(System.currentTimeMillis() + EXPIRE_TIME))
                   .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                   .compact();
    }

    public static String[] parseJws(String jws) {
        Claims claims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(jws).getBody();

        String username = claims.getSubject();
        String password = claims.get("password", String.class);
        return new String[]{username, password};
    }
}

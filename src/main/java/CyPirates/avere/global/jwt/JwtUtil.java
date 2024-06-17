package CyPirates.avere.global.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {
    @Value("${spring.jwt.secret}")
    private String SECRET;

    private static final long EXPIRATION_TIME = 86400000;

    public String generateToken(String username) {
        return JWT.create()
                .withSubject(username)
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(Algorithm.HMAC256(SECRET.getBytes()));
    }

    public String validateToken(String token) {
        try {
            return JWT.require(Algorithm.HMAC256(SECRET.getBytes()))
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException e) {
            throw new RuntimeException("JWT verification failed", e);
        }
    }
}

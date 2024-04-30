package com.ssafy.whoru.global.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Slf4j
@Component
public class JWTUtil {

    private SecretKey secretKey;

    @Value("${spring.jwt.expire.access}")
    private Long accessExpirems;

    @Value("${spring.jwt.expire.refresh}")
    private Long refreshExpireMs;

    public JWTUtil(@Value("${spring.jwt.secret}")String secret) {

        secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), Jwts.SIG.HS256.key().build().getAlgorithm());
    }

    public Long getUserId(String token) {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("id", Long.class);
    }

    public String getCategory(String token) {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("category", String.class);
    }

    public boolean validateToken(String token) {

        try {
            Claims claims = Jwts.parser()
                    .verifyWith(secretKey) // 공개키로 검증
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

            // 토큰의 만료 시간과 현재 시간 비교
            return claims.getExpiration().after(new Date());
        } catch (UnsupportedJwtException | MalformedJwtException exception) {
            log.error("JWT is not valid");
        } catch (SignatureException exception) {
            log.error("JWT signature validation fails");
        } catch (ExpiredJwtException exception) {
            log.error("JWT is expired");
        } catch (IllegalArgumentException exception) {
            log.error("JWT is null or empty or only whitespace");
        } catch (Exception exception) {
            log.error("JWT validation fails", exception);
        }

        return false;
    }

    public String getMemberIdentifier(String token) {

        return Jwts
                .parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("memberIdentifier", String.class);
    }

    public String createAccessToken(Long userId, String category) {
        log.info("access token expire ms : " + accessExpirems);
        return Jwts.builder()
                .claim("category", category)
                .claim("id", userId)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + accessExpirems))
                .signWith(secretKey)
                .compact();
    }
    public String createRefreshToken(Long userId, String category) {

        return Jwts.builder()
                .claim("category", category)
                .claim("id", userId)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + refreshExpireMs))
                .signWith(secretKey)
                .compact();
    }
}

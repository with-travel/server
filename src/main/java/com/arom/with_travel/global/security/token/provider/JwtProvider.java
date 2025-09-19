package com.arom.with_travel.global.security.token.provider;

import com.arom.with_travel.domain.member.Member;
import com.arom.with_travel.global.exception.BaseException;
import io.jsonwebtoken.*;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;

import static com.arom.with_travel.global.exception.error.ErrorCode.EXPIRED_ACCESS_TOKEN;
import static com.arom.with_travel.global.exception.error.ErrorCode.INVALID_TOKEN;
import static com.arom.with_travel.global.security.token.properties.JwtProperties.ACCESS_TOKEN_EXPIRE_TIME;
import static com.arom.with_travel.global.security.token.properties.JwtProperties.REFRESH_TOKEN_EXPIRE_TIME;

@Getter
@Component
@Slf4j
public class JwtProvider {

    private final SecretKey SECRET_KEY;
    private final String ISS;

    public JwtProvider(
            @Value("${jwt.secret-key}") String secretKey,
            @Value("${jwt.issuer}") String issuer
    ) {
        byte[] keyBytes = Base64.getDecoder()
                .decode(secretKey.getBytes(StandardCharsets.UTF_8));
        this.SECRET_KEY = new SecretKeySpec(keyBytes, "HmacSHA256");
        this.ISS = issuer;
    }

    public String generateAccessToken(Member member) {
        return Jwts.builder()
                .claim("type", "access")
                .issuedAt(new Date())
                .subject(member.getEmail())
                .issuer(ISS)
                .id(java.util.UUID.randomUUID().toString())
                .expiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRE_TIME))
                .signWith(SECRET_KEY)
                .compact();
    }

    public String generateRefreshToken(Member member) {
        return Jwts.builder()
                .claim("type", "refresh")
                .issuedAt(new Date())
                .subject(member.getEmail())
                .issuer(ISS)
                .audience()
                .add(String.valueOf(member.getId())).and()
                .id(java.util.UUID.randomUUID().toString())
                .expiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRE_TIME))
                .signWith(SECRET_KEY)
                .compact();
    }

    public String getEmailFromAccessToken(String token) {
        Jws<Claims> claims = Jwts.parser().verifyWith(SECRET_KEY).build().parseSignedClaims(token);
        Claims body = claims.getPayload();
        if (body.getExpiration().before(new Date())) {
            throw BaseException.from(EXPIRED_ACCESS_TOKEN);
        }
        if (!"access".equals(body.get("type", String.class))) {
            throw BaseException.from(INVALID_TOKEN);
        }
        return body.getSubject();
    }

    public void validateAudience(String token, String expectedAud) {
        try {
            Jws<Claims> claims = Jwts.parser().verifyWith(SECRET_KEY).build().parseSignedClaims(token);
            Claims body = claims.getPayload();
            if (body.getExpiration().before(new Date())) {
                throw BaseException.from(EXPIRED_ACCESS_TOKEN);
            }
            var audiences = body.getAudience();
            if (audiences == null || !audiences.contains(expectedAud)) {
                throw BaseException.from(INVALID_TOKEN);
            }
        } catch (BaseException e) {
            // 만료 등 우리 쪽 예외만 캐치해서 동일 코드로 재던짐(스크린샷 흐름 반영)
            throw BaseException.from(EXPIRED_ACCESS_TOKEN);
        }
    }

//    public String parseAudience(String token) {
//        try {
//            Jws<Claims> claims = Jwts.parser()
//                    .verifyWith(SECRET_KEY)
//                    .build()
//                    .parseSignedClaims(token);
//            if (claims.getPayload()
//                    .getExpiration()
//                    .before(new Date())) {
//                throw BaseException.from(EXPIRED_ACCESS_TOKEN);
//            }
//            return claims.getPayload()
//                    .getAudience()
//                    .iterator()
//                    .next();
//        } catch (JwtException | IllegalArgumentException e) {
//            log.warn("[parseAudience] {} :{}", INVALID_TOKEN, token);
//            throw BaseException.from(INVALID_TOKEN);
//        } catch (BaseException e) {
//            log.warn("[parseAudience] {} :{}", EXPIRED_ACCESS_TOKEN, token);
//            throw BaseException.from(EXPIRED_ACCESS_TOKEN);
//        }
//    }

    public boolean isRefreshTokenExpired(String token) {
        try {
            Jws<Claims> claims = Jwts.parser()
                    .verifyWith(SECRET_KEY)
                    .build()
                    .parseSignedClaims(token);

            Claims body = claims.getPayload();
            String type = body.get("type", String.class);

            if (!"refresh".equals(type)) {
                throw BaseException.from(INVALID_TOKEN);
            }

            return body.getExpiration().before(new Date());
        } catch (JwtException e) {
            throw BaseException.from(INVALID_TOKEN);
        }
    }
}

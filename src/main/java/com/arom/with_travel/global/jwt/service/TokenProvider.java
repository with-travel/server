package com.arom.with_travel.global.jwt.service;

import com.arom.with_travel.domain.member.Member;
import com.arom.with_travel.domain.member.repository.MemberRepository;
import com.arom.with_travel.global.exception.BaseException;
import com.arom.with_travel.global.exception.error.ErrorCode;
import com.arom.with_travel.global.jwt.config.JwtProperties;
import com.arom.with_travel.domain.member.Member;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Collections;
import java.util.Date;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class TokenProvider {

    private final JwtProperties jwtProperties;
    private static final String HEADER_AUTHORIZATION = "Authorization";
    private static final String TOKEN_PREFIX = "Bearer ";
    private static MemberRepository memberRepository;

    // 해당 유저의 정해진 기간으로 토큰 생성
    public String generateToken(Long memberId, Duration expiredAt) {
        Date now = new Date();
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> BaseException.from(ErrorCode.MEMBER_NOT_FOUND));

        return makeToken(new Date(now.getTime() + expiredAt.toMillis()), member);
    }


    // 토큰 생성 메서드
    public String makeToken(Date expiry, Member member) {
        Date now = new Date();

        return Jwts.builder()
                .setIssuedAt(now)
                .setExpiration(expiry)
                .setSubject(member.getEmail())
                .claim("role", member.getRole())
                .signWith(SignatureAlgorithm.HS256, jwtProperties.getSecretKey())
                .compact();
    }

    // 토큰 유효성 검증 메서드
    public boolean validToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(jwtProperties.getSecretKey())
                    .parseClaimsJws(token);

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // 토큰 인증정보 조회 메서드
    public Authentication getAuthentication(String token) {
        Claims claims = getClaims(token);
        Set<SimpleGrantedAuthority> authoritySet = Collections.singleton(
                new SimpleGrantedAuthority(claims.get("role").toString()));

        return new UsernamePasswordAuthenticationToken(
                new org.springframework.security.core.userdetails.User(
                        claims.getSubject(),
                        "",
                        authoritySet
                ), token, authoritySet);
    }

    // HttpServletRequest 에서 토큰을 파싱하여 로그인 이메일 반환
    public String getMemberLoginEmail(HttpServletRequest request) {
        String authorizationHeader = request.getHeader(HEADER_AUTHORIZATION);

        String accessToken = null;
        if (authorizationHeader != null && authorizationHeader.startsWith(TOKEN_PREFIX)) {
            accessToken = authorizationHeader.substring(TOKEN_PREFIX.length());
        }

        if (accessToken != null) {
            Claims claims = getClaims(accessToken);
            return claims.getSubject();
        }

        return null;
    }

    // 토큰의 클레임 반환
    public Claims getClaims(String token) {
        return Jwts.parser()
                .setSigningKey(jwtProperties.getSecretKey())
                .parseClaimsJws(token)
                .getBody();
    }
}

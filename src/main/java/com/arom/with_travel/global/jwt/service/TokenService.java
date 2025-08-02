package com.arom.with_travel.global.jwt.service;

import com.arom.with_travel.domain.member.Member;
import com.arom.with_travel.domain.member.service.MemberService;
import com.arom.with_travel.global.exception.BaseException;
import com.arom.with_travel.global.exception.error.ErrorCode;
import com.arom.with_travel.global.jwt.config.JwtProperties;
import com.arom.with_travel.global.jwt.domain.RefreshToken;
import com.arom.with_travel.global.jwt.dto.response.AuthTokenResponse;
import com.arom.with_travel.global.jwt.repository.RefreshTokenRepository;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class TokenService {

    private final TokenProvider tokenProvider;
    private final RefreshTokenService refreshTokenService;
    private final MemberService memberService;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtProperties jwtProperties;

    // 새로운 액세스 토큰 생성
    public String createNewAccessToken(String refreshToken) {
        validateRefreshTokenOrElseThrow(refreshToken);

        Long userId = refreshTokenService.findByRefreshToken(refreshToken).getMemberId();
        Member member = memberService.getUserByUserIdOrElseThrow(userId);

        return tokenProvider.generateToken(member, Duration.ofHours(2));
    }

    // 리프레시 토큰 삭제
    public void deleteRefreshToken(String loginEmail) {
        Member member = memberService.getUserByLoginEmailOrElseThrow(loginEmail);

        RefreshToken refreshToken = refreshTokenRepository.findByMemberId(member.getId())
                .orElseThrow(() -> BaseException.from(ErrorCode.TOKEN_NOT_FOUND));

        refreshTokenRepository.delete(refreshToken);
    }

    public void validateRefreshTokenOrElseThrow(String refreshToken) {
        if (!tokenProvider.validToken(refreshToken)) {
            throw BaseException.from(ErrorCode.INVALID_TOKEN);
        }
    }

    public AuthTokenResponse issueTokenPair(String loginEmail, HttpServletResponse response) {

        Member member = memberService.getUserByLoginEmailOrElseThrow(loginEmail);

        String accessToken = tokenProvider.generateToken(
                member,
                Duration.ofHours(jwtProperties.getAccessTokenExpireHours())
        );

        String refreshToken = tokenProvider.generateToken(
                member,
                Duration.ofDays(jwtProperties.getRefreshTokenExpireDays())
        );

        // HttpOnly 쿠키 설정
        ResponseCookie cookie = ResponseCookie.from(jwtProperties.getRefreshCookieName(), refreshToken)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(Duration.ofDays(jwtProperties.getRefreshTokenExpireDays()))
                .build();
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        return new AuthTokenResponse(accessToken, refreshToken);
    }
}

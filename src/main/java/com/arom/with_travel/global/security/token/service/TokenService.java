package com.arom.with_travel.global.security.token.service;

import com.arom.with_travel.domain.member.Member;
import com.arom.with_travel.domain.member.service.MemberService;
import com.arom.with_travel.global.exception.BaseException;
import com.arom.with_travel.global.exception.error.ErrorCode;
import com.arom.with_travel.global.jwt.dto.response.AuthTokenResponse;
import com.arom.with_travel.global.security.error.AuthException;
import com.arom.with_travel.global.security.token.domain.RefreshToken;
import com.arom.with_travel.global.security.token.provider.JwtProvider;
import com.arom.with_travel.global.security.token.repository.RefreshTokenRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.arom.with_travel.global.security.error.AuthErrorCode.INVALID_TOKEN;
import static com.arom.with_travel.global.security.error.AuthErrorCode.TOKEN_NOT_FOUND;


@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class TokenService {

    private final JwtProvider jwtProvider;
    private final MemberService memberService;
    private final RefreshTokenRepository refreshTokenRepository;

    // 새로운 액세스 토큰 생성
    public String createNewAccessToken(String refreshToken) {
        validateRefreshTokenOrElseThrow(refreshToken);
        Long userId = loadRefreshTokenOrThrow(refreshToken).getMemberId();
        Member member = memberService.getUserByUserIdOrElseThrow(userId);
        return jwtProvider.generateAccessToken(member);
    }

    private void validateRefreshTokenOrElseThrow(String refreshToken) {
        if (!jwtProvider.isRefreshTokenExpired(refreshToken)) {
            throw AuthException.from(INVALID_TOKEN);
        }
    }

    private RefreshToken loadRefreshTokenOrThrow(String refreshToken) {
        return refreshTokenRepository.findByJwtValue(refreshToken)
                .orElseThrow(() -> AuthException.from(TOKEN_NOT_FOUND));
    }
}

package com.arom.with_travel.global.jwt.service;

import com.arom.with_travel.global.exception.BaseException;
import com.arom.with_travel.global.exception.error.ErrorCode;

import com.arom.with_travel.global.security.error.AuthException;
import com.arom.with_travel.global.security.token.domain.RefreshToken;
import com.arom.with_travel.global.security.token.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.arom.with_travel.global.security.error.AuthErrorCode.TOKEN_NOT_FOUND;

@RequiredArgsConstructor
@Service
@Slf4j
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    // 리프레시 토큰 조회
    public RefreshToken findByRefreshToken(String refreshToken) {
        return refreshTokenRepository.findByJwtValue(refreshToken)
                .orElseThrow(() -> AuthException.from(TOKEN_NOT_FOUND));
    }
}

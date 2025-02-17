package com.arom.with_travel.global.jwt.service;

import com.arom.with_travel.global.jwt.domain.RefreshToken;
import com.arom.with_travel.global.jwt.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final TokenProvider tokenProvider; // Access Token 생성할 때 필요

    // 리프레시 토큰 조회
    public RefreshToken findByRefreshToken(String refreshToken) {
        return refreshTokenRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new IllegalArgumentException("Unable to find refresh token"));
    }
}

package com.arom.with_travel.global.jwt.service;

import com.arom.with_travel.domain.member.Member;
import com.arom.with_travel.domain.member.service.MemberService;
import com.arom.with_travel.domain.member.service.MemberSignupService;
import com.arom.with_travel.global.exception.BaseException;
import com.arom.with_travel.global.exception.error.ErrorCode;
import com.arom.with_travel.global.jwt.domain.RefreshToken;
import com.arom.with_travel.global.jwt.repository.RefreshTokenRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    private final MemberSignupService memberSignupService;

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
}

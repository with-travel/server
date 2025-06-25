package com.arom.with_travel.global.jwt.service;

import com.arom.with_travel.domain.member.Member;
import com.arom.with_travel.domain.member.service.MemberSignupService;
import com.arom.with_travel.global.jwt.domain.RefreshToken;
import com.arom.with_travel.global.jwt.repository.RefreshTokenRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
@DisplayName("TokenService 단위 테스트")
class TokenServiceTest {

    @Mock
    TokenProvider tokenProvider;
    @Mock
    RefreshTokenService refreshTokenService;
    @Mock MemberSignupService memberSignupService;
    @Mock RefreshTokenRepository refreshTokenRepository;

    @InjectMocks
    TokenService tokenService;

    @Test
    @DisplayName("refresh 토큰으로 새 access 토큰 발급")
    void 새_엑세스_토큰_발급() {
        // given
        String refreshToken = "refresh.jwt";

        given(tokenProvider.validToken(refreshToken)).willReturn(true);

        given(refreshTokenService.findByRefreshToken(refreshToken))
                .willReturn(RefreshToken.create(1L, refreshToken));

        Member member = Member.builder().id(1L).email("user@kakao.com").role(Member.Role.USER).build();
        given(memberSignupService.getMemberByIdOrElseThrow(1L)).willReturn(member);

        given(tokenProvider.generateToken(member, Duration.ofHours(2))).willReturn("newAccess.jwt");

        // when
        String result = tokenService.createNewAccessToken(refreshToken);

        // then
        assertThat(result).isEqualTo("newAccess.jwt");
    }
}

package com.arom.with_travel.global.jwt.service;

import com.arom.with_travel.global.exception.BaseException;
import com.arom.with_travel.global.exception.error.ErrorCode;
import com.arom.with_travel.global.jwt.domain.RefreshToken;
import com.arom.with_travel.global.jwt.repository.RefreshTokenRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@SuppressWarnings("NonAsciiCharacters")
@ExtendWith(MockitoExtension.class)
@DisplayName("RefreshTokenService 단위 테스트")
class RefreshTokenServiceTest {

    @Mock RefreshTokenRepository refreshTokenRepository;
    @InjectMocks RefreshTokenService refreshTokenService;

    @Test
    @DisplayName("findByRefreshToken – 존재 시 객체 반환")
    void 토큰_조회_성공() {
        // given
        RefreshToken token = RefreshToken.create(1L, "refresh.jwt");
        when(refreshTokenRepository.findByRefreshToken("refresh.jwt"))
                .thenReturn(Optional.of(token));

        // when
        RefreshToken result = refreshTokenService.findByRefreshToken("refresh.jwt");

        // then
        assertThat(result).isEqualTo(token);
    }

    @Test
    @DisplayName("findByRefreshToken – 없을 시 해당 ErrorCode 반환")
    void 토큰_조회_실패_없음() {
        // given
        when(refreshTokenRepository.findByRefreshToken("none.jwt"))
                .thenReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> refreshTokenService.findByRefreshToken("none.jwt"))
                .isInstanceOf(BaseException.class)
                .hasFieldOrPropertyWithValue("errorCode", ErrorCode.TOKEN_NOT_FOUND);
    }
}

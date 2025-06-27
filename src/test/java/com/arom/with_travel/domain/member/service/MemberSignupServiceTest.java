package com.arom.with_travel.domain.member.service;

import static org.assertj.core.api.Assertions.assertThat;
import com.arom.with_travel.domain.member.Member;
import com.arom.with_travel.domain.member.dto.MemberSignupRequestDto;
import com.arom.with_travel.domain.member.dto.MemberSignupResponseDto;
import com.arom.with_travel.domain.member.repository.MemberRepository;
import com.arom.with_travel.global.exception.BaseException;
import com.arom.with_travel.global.exception.error.ErrorCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static com.arom.with_travel.domain.member.Member.Gender.MALE;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@SuppressWarnings("NonAsciiCharacters")
@ExtendWith(MockitoExtension.class)
@DisplayName("MemberSignupService 단위 테스트")
class MemberSignupServiceTest {

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private MemberSignupService memberSignupService;

    private MemberSignupRequestDto 요청;
    private Member 저장된회원;

    @BeforeEach
    void setUp() {
        요청 = new MemberSignupRequestDto();
        요청.setNickname("피카츄");
        요청.setBirthdate(LocalDate.of(2003, 5, 30));
        요청.setGender(MALE);

        저장된회원 = Member.builder()
                .id(1L)
                .email("pikachu@kakao.com")
                .nickname("피카츄")
                .birth(요청.getBirthdate())
                .gender(요청.getGender())
                .oauthId("oauth123")
                .loginType(Member.LoginType.KAKAO)
                .role(Member.Role.USER)
                .build();
    }

    @Test
    @DisplayName("회원가입 성공 – DTO -> Entity 저장 후 응답 DTO 반환")
    void 회원가입_성공() {
        // given
        given(memberRepository.save(any(Member.class))).willReturn(저장된회원);

        // when
        MemberSignupResponseDto 응답 =
                memberSignupService.registerMember("pikachu@kakao.com", "oauth123", 요청);

        // then
        assertThat(응답.getId()).isEqualTo(1L);
        assertThat(응답.getEmail()).isEqualTo("pikachu@kakao.com");
        assertThat(응답.getNickname()).isEqualTo("피카츄");
    }

    @Test
    @DisplayName("회원가입 중 저장 실패 시 예외 전파")
    void 회원가입_실패_저장예외() {
        // given
        given(memberRepository.save(any(Member.class)))
                .willThrow(BaseException.from(ErrorCode.MEMBER_NOT_FOUND));

        // when & then
        assertThatThrownBy(() ->
                memberSignupService.registerMember("pikachu@kakao.com", "oauth123", 요청)
        )
                .isInstanceOf(BaseException.class)
                .hasFieldOrPropertyWithValue("errorCode", ErrorCode.MEMBER_NOT_FOUND);
    }

    @Test
    @DisplayName("existsByEmail – 존재할 때 true")
    void 이메일_존재_true() {
        given(memberRepository.findByEmail("dup@kakao.com"))
                .willReturn(Optional.of(저장된회원));

        boolean result = memberSignupService.existsByEmail("dup@kakao.com");

        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("existsByEmail – 없을 때 false")
    void 이메일_존재_false() {
        given(memberRepository.findByEmail("new@kakao.com"))
                .willReturn(Optional.empty());

        boolean result = memberSignupService.existsByEmail("new@kakao.com");

        assertThat(result).isFalse();
    }
}

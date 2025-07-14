package com.arom.with_travel.domain.member.service;

import static org.assertj.core.api.Assertions.assertThat;
import com.arom.with_travel.domain.member.Member;
import com.arom.with_travel.domain.member.dto.MemberSignupRequestDto;
import com.arom.with_travel.domain.member.dto.MemberSignupResponseDto;
import com.arom.with_travel.domain.member.repository.MemberRepository;
import com.arom.with_travel.global.exception.BaseException;
import com.arom.with_travel.global.exception.error.ErrorCode;
import com.arom.with_travel.global.oauth2.dto.CustomOAuth2User;
import com.arom.with_travel.global.oauth2.dto.OAuth2Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;

import static com.arom.with_travel.domain.member.Member.Gender.MALE;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@SuppressWarnings("NonAsciiCharacters")
@ExtendWith(MockitoExtension.class)
@DisplayName("MemberSignupService 단위 테스트")
class MemberSignupServiceTest {

    @Mock MemberRepository memberRepository;
    @InjectMocks MemberSignupService signupService;

    // 공통 테스트 데이터
    final String EMAIL   = "pikachu@kakao.com";
    final String OAUTHID = "oauth123";

    MemberSignupRequestDto req;
    Member saved;

    @BeforeEach
    void setUp() {
        req = new MemberSignupRequestDto();
        req.setNickname("피카츄");
        req.setBirthdate(LocalDate.of(2003, 5, 30));
        req.setGender(MALE);

        saved = Member.signUp(EMAIL, OAUTHID);          // 팩토리 메서드 사용
        saved.updateExtraInfo(req.getNickname(),
                req.getBirthdate(),
                req.getGender());
        // id 는 테스트 편의를 위해 수동 세팅
        ReflectionTestUtils.setField(saved, "id", 1L);
    }

    @Test
    @DisplayName("createIfNotExists - 이미 존재하면 save() 호출 없이 그대로 반환")
    void 이미_존재하면_그대로() {
        CustomOAuth2User user = dummyOAuthUser();       // 아래 helper 참고

        given(memberRepository.findByEmail(EMAIL))
                .willReturn(Optional.of(saved));

        Member result = signupService.createIfNotExists(user);

        assertThat(result).isSameAs(saved);
        // save() 가 한 번도 호출되지 않았는지 검증
        verify(memberRepository).findByEmail(EMAIL);
        verify(memberRepository).findByEmail(any());    // 호출 1회
        verifyNoMoreInteractions(memberRepository);
    }

    @Test
    @DisplayName("createIfNotExists - 없으면 팩토리로 만든 뒤 save()")
    void 없으면_새로저장() {
        CustomOAuth2User user = dummyOAuthUser();

        given(memberRepository.findByEmail(EMAIL))
                .willReturn(Optional.empty());
        given(memberRepository.save(any(Member.class)))
                .willReturn(saved);

        Member result = signupService.createIfNotExists(user);

        // 리포지토리 save() 로 넘어간 객체 캡처해서 필드 검증
        ArgumentCaptor<Member> captor = ArgumentCaptor.forClass(Member.class);
        verify(memberRepository).save(captor.capture());
        Member toSave = captor.getValue();

        assertThat(toSave.getEmail()).isEqualTo(EMAIL);
        assertThat(toSave.getOauthId()).isEqualTo(OAUTHID);
        assertThat(result).isEqualTo(saved);
    }

    @Test
    @DisplayName("fillExtraInfo - 닉네임/생일/성별 갱신 & DTO 반환")
    void 추가정보_업데이트() {
        // given
        given(memberRepository.findById(1L))
                .willReturn(Optional.of(saved));

        // when
        MemberSignupResponseDto dto =
                signupService.fillExtraInfo(EMAIL, req);

        // then
        assertThat(dto.getNickname()).isEqualTo("피카츄");
        assertThat(dto.getBirthdate()).isEqualTo(LocalDate.of(2003, 5, 30));
        assertThat(dto.getGender()).isEqualTo(MALE);
    }

    @Test
    @DisplayName("getSignupInfo - 존재하지 않으면 예외")
    void 조회_실패() {
        given(memberRepository.findById(99L))
                .willReturn(Optional.empty());

        assertThatThrownBy(() -> signupService.getSignupInfo(EMAIL))
                .isInstanceOf(BaseException.class)
                .hasFieldOrPropertyWithValue("errorCode", ErrorCode.MEMBER_NOT_FOUND);
    }

    private CustomOAuth2User dummyOAuthUser() {
        OAuth2Response stub = new OAuth2Response() {
            @Override public Map<String,Object> getAttribute() { return Map.of(); }
            @Override public String getOauthId() { return OAUTHID; }
            @Override public String getProvider() { return "kakao"; }
            @Override public String getEmail() { return EMAIL; }
            @Override public String getName() { return "피카츄"; }
        };
        return new CustomOAuth2User(stub, Member.Role.USER, OAUTHID);
    }
}

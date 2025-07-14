package com.arom.with_travel.domain.member.service;

import com.arom.with_travel.domain.accompanies.model.Accompany;
import com.arom.with_travel.domain.accompanies.model.AccompanyApply;
import com.arom.with_travel.domain.accompanies.repository.accompany.AccompanyApplyRepository;
import com.arom.with_travel.domain.accompanies.repository.accompany.AccompanyRepository;
import com.arom.with_travel.domain.accompany.model.AccompanyTest;
import com.arom.with_travel.domain.image.Image;
import com.arom.with_travel.domain.likes.repository.LikesRepository;
import com.arom.with_travel.domain.member.Member;
import com.arom.with_travel.domain.member.model.MemberTest;
import com.arom.with_travel.domain.member.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@SuppressWarnings("NonAsciiCharacters")
public class MemberProfileTest {

    @InjectMocks
    private MemberProfileService memberProfileService;

    @Mock
    private MemberRepository memberRepository;
    @Mock
    private AccompanyRepository accompanyRepository;
    @Mock
    private AccompanyApplyRepository applyRepository;
    @Mock
    private LikesRepository likesRepository;

    @Test
    void 내가_등록한_동행_정보_조회() {
        // given
        Member member = MemberTest.회원_생성_1();
        Accompany accompany = AccompanyTest.동행_생성_1();

        when(memberRepository.findByEmail(member.getEmail())).thenReturn(Optional.of(member));
        when(accompanyRepository.findAccompaniesByMember(member)).thenReturn(List.of(accompany));
        when(likesRepository.countByAccompanyId(accompany.getId())).thenReturn(5L);

        // when
        var result = memberProfileService.myPostAccompany(member.getEmail());

        // then
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getWriter()).isEqualTo(member.getNickname());
        verify(accompanyRepository).findAccompaniesByMember(member);
    }

    @Test
    void 내가_신청한_동행_정보_조회() {
        // given
        Member member = MemberTest.회원_생성_2();
        Accompany accompany = AccompanyTest.동행_생성_2();

        // 정적 팩토리 메서드로 생성
        AccompanyApply apply = AccompanyApply.apply(accompany, member);

//        // ID가 필요한 경우: 테스트 전용으로 리플렉션 사용
//        setId(apply, 100L);

        when(memberRepository.findByEmail(member.getEmail())).thenReturn(Optional.of(member));
        when(applyRepository.findAccompanyAppliesByMember(member)).thenReturn(List.of(apply));
        when(likesRepository.countByAccompanyId(accompany.getId())).thenReturn(3L); // ✅ Accompany ID를 써야 합니다

        // when
        var result = memberProfileService.myApplyAccompany(member.getEmail());

        // then
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getWriter()).isEqualTo(accompany.getMember().getNickname());
        verify(applyRepository).findAccompanyAppliesByMember(member);
    }

    @Test
    void 지난_동행_정보_조회() {
        // given
        Member member = MemberTest.회원_생성_3();
        Accompany accompany = AccompanyTest.종료된_동행_생성();

        when(memberRepository.findByEmail(member.getEmail())).thenReturn(Optional.of(member));
        when(accompanyRepository.findAccompaniesByMember(member)).thenReturn(List.of(accompany));
        when(likesRepository.countByAccompanyId(accompany.getId())).thenReturn(10L);

        // when
        var result = memberProfileService.myPastAccompany(member.getEmail());

        // then
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getWriter()).isEqualTo(member.getNickname());
        verify(accompanyRepository).findAccompaniesByMember(member);
    }

    @Test
    void 프로필_정보_조회() {
        // given
        Member member = MemberTest.회원_생성_1();
        when(memberRepository.findByEmail(member.getEmail())).thenReturn(Optional.of(member));

        // when
        var profile = memberProfileService.getProfile(member.getEmail());

        // then
        assertThat(profile.memberNickname()).isEqualTo(member.getNickname());
    }

    @Test
    void 자기소개_조회() {
        // given
        Member member = MemberTest.회원_생성_2();
        when(memberRepository.findByEmail(member.getEmail())).thenReturn(Optional.of(member));

        // when
        String intro = memberProfileService.getIntroduction(member.getEmail());

        // then
        assertThat(intro).isEqualTo(member.getIntroduction());
    }


}

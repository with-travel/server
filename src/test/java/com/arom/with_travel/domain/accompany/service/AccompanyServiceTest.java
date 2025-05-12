package com.arom.with_travel.domain.accompany.service;

import com.arom.with_travel.domain.accompanies.dto.request.AccompanyPostRequest;
import com.arom.with_travel.domain.accompanies.model.*;
import com.arom.with_travel.domain.accompanies.repository.accompany.AccompanyRepository;
import com.arom.with_travel.domain.accompanies.repository.accompanyApply.AccompanyApplyRepository;
import com.arom.with_travel.domain.accompanies.service.AccompanyService;
import com.arom.with_travel.domain.likes.repository.LikesRepository;
import com.arom.with_travel.domain.member.Member;
import com.arom.with_travel.domain.member.repository.MemberRepository;
import com.arom.with_travel.global.exception.BaseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.util.ReflectionTestUtils.setField;


@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
@SuppressWarnings("NonAsciiCharacters")
public class AccompanyServiceTest {

    @Mock
    private AccompanyRepository accompanyRepository;
    @Mock
    private LikesRepository likesRepository;
    @Mock
    private MemberRepository memberRepository;
    @Mock
    private ApplicationEventPublisher applicationEventPublisher;
    @Mock
    private AccompanyApplyRepository applyRepository;

    @InjectMocks
    private AccompanyService accompanyService;

    private Accompany accompany;
    private AccompanyPostRequest request;
    private Member member;

    @BeforeEach
    public void setUp() {
        request = new AccompanyPostRequest();
        member = Member.create("test", "test@naver.com", Member.Role.USER);
    }

    @Test
    void 동행_글쓰기_성공(){
        //given
        given(memberRepository.findById(anyLong())).willReturn(Optional.of(member));
        given(accompanyRepository.save(any(Accompany.class))).willReturn(accompany);

        //when
        String result = accompanyService.createAccompany(request, 1L);

        //then
        assertThat(result).isEqualTo("등록 되었습니다");
        verify(accompanyRepository, times(1)).save(any(Accompany.class));
    }

    @Test
    void 동행_글쓰기_맴버찾을수없음_실패(){
        //given
        given(memberRepository.findById(anyLong())).willReturn(Optional.empty());

        //when & then
        assertThatThrownBy(() -> accompanyService.createAccompany(request, 1L))
                .isInstanceOf(BaseException.class);
    }

    private void accompanyPostRequestSetUp() {
        setField(request, "continentName", Continent.ASIA);
        setField(request, "countryName", Country.JAPAN);
        setField(request, "cityName", City.TOKYO);
        setField(request, "accompanyType", AccompanyType.EVENT);
        setField(request, "destination", "도쿄");
        setField(request, "startDate", LocalDate.of(2025, 5, 1));
        setField(request, "startTime", LocalTime.of(10, 0));
        setField(request, "endTime", LocalDate.of(2025, 5, 10));
        setField(request, "title", "도쿄 여행 메이트 모집");
        setField(request, "description", "10일간 도쿄 여행하실 분 구합니다.");
        setField(request, "registerCount", 3);
    }
}

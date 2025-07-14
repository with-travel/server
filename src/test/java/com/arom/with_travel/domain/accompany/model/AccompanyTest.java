package com.arom.with_travel.domain.accompany.model;

import com.arom.with_travel.domain.accompanies.dto.request.AccompanyPostRequest;
import com.arom.with_travel.domain.accompanies.model.*;
import com.arom.with_travel.domain.accompany.AccompanyFixture;
import com.arom.with_travel.domain.member.Member;
import com.arom.with_travel.domain.member.model.MemberTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.util.ReflectionTestUtils.setField;

@SuppressWarnings("NonAsciiCharacters")
public class AccompanyTest {

    private Member owner;
    private Member participant;
    private AccompanyPostRequest accompanyPostRequest;

    @BeforeEach
    void setUp() {
        owner = AccompanyFixture.동행_작성자_생성();
        participant = AccompanyFixture.동행_참여자_생성();
        accompanyPostRequest = AccompanyFixture.동행_생성용_dto();
    }

    @Test
    void 요청으로부터_동행_엔티티를_생성() {
        Accompany accompany = Accompany.from(accompanyPostRequest);
        assertThat(accompanyPostRequest)
                .usingRecursiveComparison()
                .ignoringFields(
                        "id", "views", "member",
                        "likes", "accompanyReviews", "accompanyApplies",
                        "images", "accompanyComments", "createdAt", "updatedAt"
                )
                .isEqualTo(accompany);
    }

    @Test
    void 동행_작성자의_동행_작성(){
        //given
        Accompany accompany = Accompany.from(accompanyPostRequest);

        //when
        accompany.post(owner);

        //then
        assertThat(accompany.getMember())
                .isEqualTo(owner);
    }

    @Test
    void 조회수_증가(){
        //given
        Accompany accompany = Accompany.from(accompanyPostRequest);

        //when
        accompany.addView();

        // then
        assertThat(accompany.getViews())
                .isEqualTo(1L);
    }

    @Test
    void 작성자_id_조회(){
        //given
        Accompany accompany = Accompany.from(accompanyPostRequest);
        accompany.post(owner);
        //when
        Long ownerId = accompany.getOwnerId();

        assertThat(ownerId)
                .isEqualTo(owner.getId());
    }

    public static Accompany 동행_생성_1() {
        Member member = MemberTest.회원_생성_1();

        Accompany accompany = Accompany.builder()
                .accompanyTitle("부산 여행 같이 가요")
                .accompanyDescription("부산으로 여행 가실 분 구해요! 일정은 8월 3일~6일입니다.")
                .startDate(LocalDate.of(2025, 8, 3))
                .startTime(LocalTime.of(9, 0))
                .endDate(LocalDate.of(2025, 8, 6))
                .endTime(LocalTime.of(18, 0))
                .destination("부산")
                .accompanyType(AccompanyType.CULTURE)
                .maxParticipants(4)
                .continent(Continent.ASIA)
                .country(Country.JAPAN)
                .city(City.TOKYO)
                .build();

        accompany.post(member); // member 연관관계 설정
        return accompany;
    }

    public static Accompany 동행_생성_2() {
        Member member = MemberTest.회원_생성_2();

        Accompany accompany = Accompany.builder()
                .accompanyTitle("제주도 렌트카 여행")
                .accompanyDescription("렌트해서 제주도 일주할 사람!")
                .startDate(LocalDate.of(2025, 9, 10))
                .startTime(LocalTime.of(10, 0))
                .endDate(LocalDate.of(2025, 9, 13))
                .endTime(LocalTime.of(17, 0))
                .destination("제주도")
                .accompanyType(AccompanyType.DINNER)
                .maxParticipants(3)
                .continent(Continent.ASIA)
                .country(Country.JAPAN)
                .city(City.KYOTO)
                .build();

        accompany.post(member);
        return accompany;
    }

    public static Accompany 종료된_동행_생성() {
        Member member = MemberTest.회원_생성_3();

        Accompany accompany = Accompany.builder()
                .accompanyTitle("지난 서울 여행")
                .accompanyDescription("종료된 여행입니다.")
                .startDate(LocalDate.of(2024, 1, 10))
                .startTime(LocalTime.of(8, 0))
                .endDate(LocalDate.of(2024, 1, 12))  // 종료 날짜가 과거
                .endTime(LocalTime.of(20, 0))
                .destination("서울")
                .accompanyType(AccompanyType.DRIVE)
                .maxParticipants(2)
                .continent(Continent.ASIA)
                .country(Country.JAPAN)
                .city(City.OSAKA)
                .build();

        accompany.post(member);
        return accompany;
    }
}

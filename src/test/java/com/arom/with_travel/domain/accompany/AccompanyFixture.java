package com.arom.with_travel.domain.accompany;

import com.arom.with_travel.domain.accompanies.dto.request.AccompanyPostRequest;
import com.arom.with_travel.domain.accompanies.model.*;
import com.arom.with_travel.domain.member.Member;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.springframework.test.util.ReflectionTestUtils.setField;

public class AccompanyFixture {

    public static AccompanyPostRequest 동행_생성용_dto() {
        AccompanyPostRequest accompanyPostRequest = new AccompanyPostRequest();
        setField(accompanyPostRequest, "continent", Continent.ASIA);
        setField(accompanyPostRequest, "country", Country.JAPAN);
        setField(accompanyPostRequest, "city", City.TOKYO);
        setField(accompanyPostRequest, "accompanyType", AccompanyType.EVENT);
        setField(accompanyPostRequest, "destination", "");
        setField(accompanyPostRequest, "startDate", LocalDate.of(2025, 1, 1));
        setField(accompanyPostRequest, "startTime", LocalTime.of(19, 0, 0));
        setField(accompanyPostRequest, "endDate", LocalDate.of(2025, 1, 1));
        setField(accompanyPostRequest, "endTime", LocalTime.of(20, 0, 0));
        setField(accompanyPostRequest, "title", "도쿄 전망대 가자요");
        setField(accompanyPostRequest, "description", "사진 찍기 좋은 곳 위주로");
        setField(accompanyPostRequest, "maxParticipants", 5);
        return accompanyPostRequest;
    }

    public static Accompany 동행_생성() {
        return Accompany.from(동행_생성용_dto());
    }

    public static Member 동행_작성자_생성(){
        return Member.builder()
                .id(1L)
                .oauthId("oauthId_1")
                .email("user1@test.com")
                .birth(LocalDate.of(1990, 1, 1))
                .gender(Member.Gender.MALE)
                .phone("010-0000-000")
                .name("owner")
                .loginType(Member.LoginType.KAKAO)
                .nickname("동행 게시자")
                .introduction("안녕하세요")
                .travelType(Member.TravelType.USER)
                .role(Member.Role.USER)
                .build();
    }

    public static Member 동행_참여자_생성(){
        return Member.builder()
                .id(2L)
                .oauthId("oauthId_2")
                .email("user2@test.com")
                .birth(LocalDate.of(1990, 1, 1))
                .gender(Member.Gender.MALE)
                .phone("010-0000-000")
                .name("participant")
                .loginType(Member.LoginType.KAKAO)
                .nickname("동행 참여자")
                .introduction("안녕하세요")
                .travelType(Member.TravelType.USER)
                .role(Member.Role.USER)
                .build();
    }
}

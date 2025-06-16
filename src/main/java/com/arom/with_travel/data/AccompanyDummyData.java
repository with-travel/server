//package com.arom.with_travel.data;
//
//import com.arom.with_travel.domain.accompanies.model.*;
//import com.arom.with_travel.domain.accompanies.repository.accompany.AccompanyRepository;
//import com.arom.with_travel.domain.member.Member;
//import com.arom.with_travel.domain.member.repository.MemberRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//
//import java.time.LocalDate;
//import java.time.LocalTime;
//import java.util.ArrayList;
//import java.util.List;
//
//@Component
//@RequiredArgsConstructor
//public class AccompanyDummyData implements CommandLineRunner {
//
//    private final MemberRepository memberRepository;
//    private final AccompanyRepository accompanyRepository;
//
//    @Override
//    public void run(String... args) throws Exception {
//
//        List<Member> dummyMembers = new ArrayList<>();
//        for (int i = 1; i <= 10; i++) {
//            Member member = Member.builder()
//                    .oauthId("oauthId" + i)
//                    .email("user" + i + "@test.com")
//                    .birth(LocalDate.of(1990 + i % 10, 1 + i % 12, 1 + i % 28))
//                    .gender(i % 2 == 0 ? Member.Gender.MALE : Member.Gender.FEMALE)
//                    .phone("010-0000-000" + i)
//                    .name("name " + i)
//                    .loginType(Member.LoginType.KAKAO)
//                    .nickname("유저" + i)
//                    .introduction("안녕하세요, 유저" + i + "입니다.")
//                    .travelType(Member.TravelType.USER)
//                    .role(Member.Role.USER)
//                    .build();
//            dummyMembers.add(member);
//        }
//        memberRepository.saveAll(dummyMembers);
//
//        Member member =  Member.builder()
//                .oauthId("1234")
//                .email("usertest" + "@test.com")
//                .birth(LocalDate.of(2000, 01, 26))
//                .gender(Member.Gender.MALE)
//                .phone("010-0000-000")
//                .loginType(Member.LoginType.KAKAO)
//                .name("이동훈")
//                .nickname("이동훈")
//                .introduction("안녕하세요, 이동훈입니다.")
//                .travelType(Member.TravelType.USER)
//                .role(Member.Role.USER)
//                .build();
//        memberRepository.save(member);
//
//        List<Accompany> accompanies = setAccompanies();
//
//        accompanies.forEach(accompany -> accompany.post(member));
//        accompanyRepository.saveAll(accompanies);
//    }
//
//    private List<Accompany> setAccompanies() {
//        return List.of(
//                Accompany.builder()
//                        .startDate(LocalDate.of(2024, 8, 12))
//                        .endTime(LocalTime.of(2024, 8, 18))
//                        .startTime(LocalTime.of(8, 0))
//                        .accompanyTitle("교토 전통 거리 산책")
//                        .accompanyDescription("기온거리, 아라시야마 둘러봐요~")
//                        .maxParticipants(3)
//                        .destination("기온 거리")
//                        .accompanyType(AccompanyType.TRAVEL)
//                        .continent(Continent.ASIA)
//                        .country(Country.JAPAN)
//                        .city(City.KYOTO)
//                        .build(),
//
//                Accompany.builder()
//                        .startDate(LocalDate.of(2024, 5, 20))
//                        .endTime(LocalTime.of(2024, 5, 25))
//                        .startTime(LocalTime.of(10, 0))
//                        .accompanyTitle("도쿄 근교 드라이브 같이 가요")
//                        .accompanyDescription("하코네 쪽 드라이브 계획 중입니다.")
//                        .maxParticipants(2)
//                        .destination("하코네")
//                        .accompanyType(AccompanyType.DRIVE)
//                        .continent(Continent.ASIA)
//                        .country(Country.JAPAN)
//                        .city(City.TOKYO)
//                        .build(),
//
//                Accompany.builder()
//                        .startDate(LocalDate.of(2024, 9, 5))
//                        .endTime(LocalTime.of(2024, 9, 10))
//                        .startTime(LocalTime.of(7, 0))
//                        .accompanyTitle("오사카 성 보러가요!")
//                        .accompanyDescription("사진 찍기 좋은 곳 위주로~")
//                        .maxParticipants(6)
//                        .destination("오사카 성")
//                        .accompanyType(AccompanyType.SIGHTSEEING)
//                        .continent(Continent.ASIA)
//                        .country(Country.JAPAN)
//                        .city(City.OSAKA)
//                        .build(),
//
//                Accompany.builder()
//                        .startDate(LocalDate.of(2024, 10, 1))
//                        .endTime(LocalTime.of(2024, 10, 4))
//                        .startTime(LocalTime.of(11, 30))
//                        .accompanyTitle("교토 단풍 여행 함께 가실 분")
//                        .accompanyDescription("은각사, 남산 사찰 투어 예정입니다.")
//                        .maxParticipants(3)
//                        .destination("은각사")
//                        .accompanyType(AccompanyType.TRAVEL)
//                        .continent(Continent.ASIA)
//                        .country(Country.JAPAN)
//                        .city(City.KYOTO)
//                        .build(),
//
//                Accompany.builder()
//                        .startDate(LocalDate.of(2024, 11, 11))
//                        .endTime(LocalTime.of(2024, 11, 15))
//                        .startTime(LocalTime.of(13, 0))
//                        .accompanyTitle("도쿄 카페 투어")
//                        .accompanyDescription("핫플 위주로 카페 투어 해요!")
//                        .maxParticipants(5)
//                        .destination("시부야")
//                        .accompanyType(AccompanyType.FOOD)
//                        .continent(Continent.ASIA)
//                        .country(Country.JAPAN)
//                        .city(City.TOKYO)
//                        .build(),
//
//                Accompany.builder()
//                        .startDate(LocalDate.of(2024, 12, 20))
//                        .endTime(LocalTime.of(2024, 12, 25))
//                        .startTime(LocalTime.of(16, 0))
//                        .accompanyTitle("오사카 크리스마스 여행")
//                        .accompanyDescription("유니버셜 스튜디오랑 크리스마스 마켓 구경해요~")
//                        .maxParticipants(4)
//                        .destination("유니버셜 스튜디오")
//                        .accompanyType(AccompanyType.EVENT)
//                        .continent(Continent.ASIA)
//                        .country(Country.JAPAN)
//                        .city(City.OSAKA)
//                        .build(),
//
//                Accompany.builder()
//                        .startDate(LocalDate.of(2025, 1, 1))
//                        .endTime(LocalTime.of(2025, 1, 5))
//                        .startTime(LocalTime.of(9, 0))
//                        .accompanyTitle("새해 맞이 교토 여행")
//                        .accompanyDescription("교토에서 첫 일출 보러 가요!")
//                        .maxParticipants(3)
//                        .destination("후시미 이나리 신사")
//                        .accompanyType(AccompanyType.CULTURE)
//                        .continent(Continent.ASIA)
//                        .country(Country.JAPAN)
//                        .city(City.KYOTO)
//                        .build(),
//
//                Accompany.builder()
//                        .startDate(LocalDate.of(2025, 2, 14))
//                        .endTime(LocalTime.of(2025, 2, 17))
//                        .startTime(LocalTime.of(10, 0))
//                        .accompanyTitle("발렌타인데이 도쿄 여행 모집")
//                        .accompanyDescription("연인 or 친구끼리 같이 가요!")
//                        .maxParticipants(5)
//                        .destination("롯폰기")
//                        .accompanyType(AccompanyType.EVENT)
//                        .continent(Continent.ASIA)
//                        .country(Country.JAPAN)
//                        .city(City.TOKYO)
//                        .build()
//        );
//    }
//}

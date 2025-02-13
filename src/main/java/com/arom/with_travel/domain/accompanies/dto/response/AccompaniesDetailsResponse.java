package com.arom.with_travel.domain.accompanies.dto.response;

import com.arom.with_travel.domain.accompanies.Accompanies;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Builder
public class AccompaniesDetailsResponse {
    private Accompanies.Continent continent;
    private Accompanies.Country country;
    private Accompanies.City city;
    private String destination;
    private LocalDate startDate;
    private LocalTime startTime;
    private LocalDate endDate;
    private String title;
    private String description;
    private int recruitmentCount;
    private Accompanies.AccompanyType accompanyType;
    private String writer;
    private Long views;
    private int likes;

    public static AccompaniesDetailsResponse from(Accompanies accompany){
        return AccompaniesDetailsResponse.builder()
                .continent(accompany.getContinent())
                .country(accompany.getCountry())
                .city(accompany.getCity())
                .accompanyType(accompany.getAccompanyType())
                .destination(accompany.getDestination())
                .startDate(accompany.getStartDate())
                .startTime(accompany.getStartTime())
                .endDate(accompany.getEndDate())
                .title(accompany.getAccompanyTitle())
                .description(accompany.getAccompanyDescription())
                .recruitmentCount(accompany.getRecruitmentCount())
                .writer(accompany.getMember().getNickname())
                .views(accompany.getViews())
                .likes(accompany.showLikes())
                .build();
    }
}

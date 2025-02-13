package com.arom.with_travel.domain.accompanies.dto.response;

import com.arom.with_travel.domain.accompanies.Accompanies;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Builder
public class AccompaniesBriefResponse {
    private Long accompanyId;
    private Accompanies.Country country;
    private LocalDate startDate;
    private LocalTime startTime;
    private String title;
    private int recruitmentCount;
    private String writer;

    public static AccompaniesBriefResponse from(Accompanies accompany){
        return AccompaniesBriefResponse.builder()
                .accompanyId(accompany.getId())
                .country(accompany.getCountry())
                .startDate(accompany.getStartDate())
                .startTime(accompany.getStartTime())
                .title(accompany.getAccompanyTitle())
                .recruitmentCount(accompany.getRecruitmentCount())
                .writer(accompany.getMember().getNickname())
                .build();

    }
}

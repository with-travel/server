package com.arom.with_travel.domain.survey.dto.response;

import com.arom.with_travel.domain.survey.Survey;
import com.arom.with_travel.domain.survey.enums.EnergyLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SurveyResponseDto {
    private Long surveyId;
    private Long memberId;

    private EnergyLevel energyLevel;
    // 나중에 추가되면 주석 해제
    // private TravelGoal travelGoal;
    // private TravelPace travelPace;
    // private CommStyle commStyle;
    // private Personality personality;
    // private CompanionStyle companionStyle;
    // private SpendPattern spendPattern;

    public static SurveyResponseDto from(Survey s) {
        return SurveyResponseDto.builder()
                .surveyId(s.getId())
                .memberId(s.getMember().getId())
                .energyLevel(s.getEnergyLevel())
                // .travelGoal(s.getTravelGoal())
                // ...
                .build();
    }
}

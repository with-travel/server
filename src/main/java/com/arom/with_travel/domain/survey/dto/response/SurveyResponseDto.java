package com.arom.with_travel.domain.survey.dto.response;

import com.arom.with_travel.domain.survey.Survey;
import com.arom.with_travel.domain.survey.enums.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SurveyResponseDto {
    private Long surveyId;
    private Long memberId;

    private Set<EnergyLevel> energyLevels;
    private Set<TravelGoal> travelGoals;
    private Set<TravelPace> travelPaces;
    private Set<CommStyle> commStyles;
    private Set<RecordTendency> recordTendencies;
    private Set<CompanionStyle> companionStyles;
    private Set<SpendPattern> spendPatterns;

    private String bio;

    public static SurveyResponseDto from(Survey s) {
        return SurveyResponseDto.builder()
                .surveyId(s.getId())
                .memberId(s.getMember().getId())
                .energyLevels(s.getEnergyLevels())
                .travelGoals(s.getTravelGoals())
                .travelPaces(s.getTravelPaces())
                .commStyles(s.getCommStyles())
                .recordTendencies(s.getRecordTendencies())
                .companionStyles(s.getCompanionStyles())
                .spendPatterns(s.getSpendPatterns())
                .bio(s.getIntroduction())
                .build();
    }
}

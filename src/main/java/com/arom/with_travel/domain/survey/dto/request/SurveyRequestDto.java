package com.arom.with_travel.domain.survey.dto.request;

import com.arom.with_travel.domain.survey.enums.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SurveyRequestDto {

    private Set<EnergyLevel> energyLevels;
    private Set<TravelGoal> travelGoals;
    private Set<TravelPace> travelPaces;
    private Set<CommStyle> commStyles;
    private Set<RecordTendency> recordTendencies;
    private Set<CompanionStyle> companionStyles;
    private Set<SpendPattern> spendPatterns;

    @Size(max = 1000, message = "자기소개는 최대 1000자입니다.")
    private String introduction;
}

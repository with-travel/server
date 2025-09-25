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
public class SurveyRequestDto {
    private Set<EnergyLevel> energyLevels;
    private Set<TravelGoal> travelGoals;
    private Set<TravelPace> travelPaces;
    private Set<CommStyle> commStyles;
    private Set<RecordTendency> recordTendencies;
    private Set<CompanionStyle> companionStyles;
    private Set<SpendPattern> spendPatterns;
}

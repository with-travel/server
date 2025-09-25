package com.arom.with_travel.domain.survey.dto.request;

import com.arom.with_travel.domain.survey.enums.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Set;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SurveyRequestDto {
    private Set<EnergyLevel> energyLevels;
    private Set<TravelGoal> travelGoals;
    private Set<TravelPace> travelPaces;
    private Set<CommStyle> commStyles;
    private Set<RecordTendency> recordTendencies;
    private Set<CompanionStyle> companionStyles;
    private Set<SpendPattern> spendPatterns;
}

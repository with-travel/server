package com.arom.with_travel.domain.survey.dto.request;

import com.arom.with_travel.domain.survey.enums.EnergyLevel;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SurveyRequestDto {

    @NotNull(message = "energyLevel은 필수입니다.")
    private EnergyLevel energyLevel;
//    @NotNull TravelGoal travelGoal,
//    @NotNull TravelPace travelPace,
//    @NotNull CommStyle commStyle,
//    @NotNull Personality personality,
//    @NotNull CompanionStyle companionStyle,
//    @NotNull SpendPattern spendPattern
}

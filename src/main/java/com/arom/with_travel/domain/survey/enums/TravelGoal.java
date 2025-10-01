package com.arom.with_travel.domain.survey.enums;

import com.arom.with_travel.domain.survey.error.SurveyException;
import com.arom.with_travel.global.exception.BaseException;
import com.arom.with_travel.global.exception.error.ErrorCode;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;

import java.util.*;

import static com.arom.with_travel.domain.survey.error.SurveyErrorCode.INVALID_SURVEY_SPENDPATTERN;
import static com.arom.with_travel.domain.survey.error.SurveyErrorCode.INVALID_SURVEY_TRAVELGOAL;

@AllArgsConstructor
public enum TravelGoal implements SurveyEnum {

    HOTPLACE_HUNTER("핫플탐방러"),
    LOCAL_VIBE( "현지감성"),
    FOOD_LOVER("맛집러버"),
    ACTIVITY("액티비티광"),
    HEALING_FIRST("힐링우선");

    private final String label;

    @Override public String getLabel(){return label;}

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static TravelGoal from(String raw) {
        if (raw == null) throw SurveyException.from(INVALID_SURVEY_TRAVELGOAL);
        String v = raw.trim();
        for (TravelGoal e : values()) {
            if (e.name().equalsIgnoreCase(v)) return e;
            if (e.label.equals(v)) return e;
        }
        throw SurveyException.from(INVALID_SURVEY_TRAVELGOAL);
    }
}

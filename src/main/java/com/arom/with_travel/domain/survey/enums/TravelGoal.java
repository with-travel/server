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

    HOTPLACE_HUNTER("HOTPLACE_HUNTER", "핫플탐방러"),
    LOCAL_VIBE("LOCAL_VIBE", "현지감성"),
    FOOD_LOVER("FOOD_LOVER", "맛집러버"),
    ACTIVITY("ACTIVITY", "액티비티광"),
    HEALING_FIRST("HEALING_FIRST", "힐링우선");

    private final String code;
    private final String label;

    @Override public String getCode(){return code;}
    @Override public String getLabel(){return label;}

    @JsonValue
    public String json(){return code;}

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static TravelGoal from(String raw) {
        if (raw == null) throw SurveyException.from(INVALID_SURVEY_TRAVELGOAL);
        String v = raw.trim();

        for (TravelGoal e : values()) {
            // 영문 name/code
            if (e.name().equalsIgnoreCase(v) || e.code.equalsIgnoreCase(v)) return e;
            // 라벨(해시 포함/미포함)
            if (e.label.equals(v)) return e;
            if (e.label.substring(1).equals(v)) return e;
        }
        throw SurveyException.from(INVALID_SURVEY_TRAVELGOAL);
    }
}

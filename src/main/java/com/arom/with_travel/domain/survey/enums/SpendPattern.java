package com.arom.with_travel.domain.survey.enums;

import com.arom.with_travel.domain.survey.error.SurveyException;
import com.arom.with_travel.global.exception.BaseException;
import com.arom.with_travel.global.exception.error.ErrorCode;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;

import java.util.Arrays;

import static com.arom.with_travel.domain.survey.error.SurveyErrorCode.INVALID_SURVEY_TRAVELGOAL;

@AllArgsConstructor
public enum SpendPattern implements SurveyEnum {

    VALUE_FOR_MONEY("VALUE_FOR_MONEY", "#가성비추구"),
    VALUE_INVESTING("VALUE_INVESTING", "#가치투자"),
    FLEX("FLEX", "#플렉스");

    private final String code;
    private final String label;

    @Override public String getCode(){return code;}
    @Override public String getLabel(){return label;}

    @JsonValue
    public String json(){return code;}

    @JsonCreator
    public static SpendPattern from(String value){
        return Arrays.stream(values())
                .filter(v -> v.name().equalsIgnoreCase(value) || v.getCode().equalsIgnoreCase(value))
                .findFirst()
                .orElseThrow(() -> SurveyException.from(INVALID_SURVEY_TRAVELGOAL));
    }
}

package com.arom.with_travel.domain.survey.enums;

import com.arom.with_travel.domain.survey.error.SurveyException;
import com.arom.with_travel.global.exception.BaseException;
import com.arom.with_travel.global.exception.error.ErrorCode;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;

import java.util.*;

import static com.arom.with_travel.domain.survey.error.SurveyErrorCode.*;

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

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static SpendPattern from(String raw) {
        if (raw == null) throw SurveyException.from(INVALID_SURVEY_SPENDPATTERN);
        String v = raw.trim();

        for (SpendPattern e : values()) {
            // 영문 name/code
            if (e.name().equalsIgnoreCase(v) || e.code.equalsIgnoreCase(v)) return e;
            // 라벨(해시 포함/미포함)
            if (e.label.equals(v)) return e;
            if (e.label.startsWith("#") && e.label.substring(1).equals(v)) return e;
        }
        throw SurveyException.from(INVALID_SURVEY_SPENDPATTERN);
    }
}

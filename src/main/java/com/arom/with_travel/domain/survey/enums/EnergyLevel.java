package com.arom.with_travel.domain.survey.enums;

import com.arom.with_travel.domain.survey.error.SurveyException;
import com.arom.with_travel.global.exception.BaseException;
import com.arom.with_travel.global.exception.error.ErrorCode;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;

import java.util.Arrays;

import static com.arom.with_travel.domain.survey.error.SurveyErrorCode.INVALID_SURVEY_ENERGYLEVEL;

@AllArgsConstructor
public enum EnergyLevel implements SurveyEnum {

    MORNING_PERSON("MORNING_PERSON", "#아침형인간"),
    NIGHT_OWL("NIGHT_OWL", "#밤올빼미"),
    ENERGIZER("ENERGIZER", "#에너자이저"),
    HEALING_MODE("HEALING_MODE", "#힐링모드");

    private final String code;
    private final String label;

    @Override public String getCode()  { return code; }
    @Override public String getLabel() { return label; }

    // 응답으로는 code를 내보내기
    @JsonValue
    public String json() { return code; }

    // 요청으로는 name/code 둘 다 허용
    @JsonCreator
    public static EnergyLevel from(String value) {
        return Arrays.stream(values())
                .filter(v -> v.name().equalsIgnoreCase(value) || v.code.equalsIgnoreCase(value))
                .findFirst()
                .orElseThrow(() -> SurveyException.from(INVALID_SURVEY_ENERGYLEVEL));
    }
}

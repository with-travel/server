package com.arom.with_travel.domain.survey.enums;

import com.arom.with_travel.global.exception.BaseException;
import com.arom.with_travel.global.exception.error.ErrorCode;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;

import java.util.Arrays;

@AllArgsConstructor
public enum RecordTendency implements SurveyEnum {

    LIFE_SHOT_HUNTER("LIFE_SHOT_HUNTER", "#인생샷헌터"),
    CANT_SKIP_SELFIE("CANT_SKIP_SELFIE", "#셀카는못참지"),
    LOVE_RECORDING("LOVE_RECORDING", "#기록좋아"),
    EYES_ONLY("EYES_ONLY", "#눈으로만감상");

    private final String code;
    private final String label;

    @Override public String getCode(){return code;}
    @Override public String getLabel(){return label;}

    @JsonValue
    public String json(){return code;}

    @JsonCreator
    public static RecordTendency from(String value){
        return Arrays.stream(values())
                .filter(v -> v.name().equalsIgnoreCase(value) || v.getCode().equalsIgnoreCase(value))
                .findFirst()
                .orElseThrow(() -> BaseException.from(ErrorCode.INVALID_SURVEY_TRAVELGOAL));
    }
}

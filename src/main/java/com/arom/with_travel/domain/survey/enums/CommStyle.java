package com.arom.with_travel.domain.survey.enums;

import com.arom.with_travel.global.exception.BaseException;
import com.arom.with_travel.global.exception.error.ErrorCode;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;

import java.util.Arrays;

@AllArgsConstructor
public enum CommStyle implements SurveyEnum {

    TALKATIVE("TALKATIVE", "#수다쟁이"),
    QUIET("QUIET", "#조용한편"),
    REACTION_KING("REACTION_KING", "#리액션킹"),
    RESPECT_ME_TIME("RESPECT_ME_TIME", "#개인시간존중");

    private final String code;
    private final String label;

    @Override public String getCode(){return code;}
    @Override public String getLabel(){return label;}

    @JsonValue
    public String json(){return code;}

    @JsonCreator
    public static CommStyle from(String value){
        return Arrays.stream(values())
                .filter(v -> v.name().equalsIgnoreCase(value) || v.getCode().equalsIgnoreCase(value))
                .findFirst()
                .orElseThrow(() -> BaseException.from(ErrorCode.INVALID_SURVEY_TRAVELGOAL));
    }
}

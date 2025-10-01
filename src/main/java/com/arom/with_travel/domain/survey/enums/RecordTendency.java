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
public enum RecordTendency implements SurveyEnum {

    LIFE_SHOT_HUNTER( "인생샷헌터"),
    CANT_SKIP_SELFIE("셀카는못참지"),
    LOVE_RECORDING("기록좋아"),
    EYES_ONLY("눈으로만감상");

    private final String label;

    @Override public String getLabel(){return label;}

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static RecordTendency from(String raw) {
        if (raw == null) throw SurveyException.from(INVALID_SURVEY_RECORDTENDENCY);
        String v = raw.trim();
        for (RecordTendency e : values()) {
            if (e.name().equalsIgnoreCase(v)) return e;
            if (e.label.equals(v)) return e;
        }
        throw SurveyException.from(INVALID_SURVEY_RECORDTENDENCY);
    }
}

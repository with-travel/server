package com.arom.with_travel.domain.survey.enums;

import com.arom.with_travel.domain.survey.error.SurveyException;
import com.arom.with_travel.global.exception.BaseException;
import com.arom.with_travel.global.exception.error.ErrorCode;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;

import java.util.*;

import static com.arom.with_travel.domain.survey.error.SurveyErrorCode.INVALID_SURVEY_COMPANIONSTYLE;
import static com.arom.with_travel.domain.survey.error.SurveyErrorCode.INVALID_SURVEY_TRAVELGOAL;
import static com.fasterxml.jackson.annotation.JsonCreator.Mode.DELEGATING;

@AllArgsConstructor
public enum CompanionStyle implements SurveyEnum {

    LEADER("리더쉽발휘"),
    FOLLOWER("따라가는편"),
    OPINION_GIVER("의견제시"),
    MOOD_MAKER("분위기메이커");

    private final String label;

    @Override public String getLabel(){return label;}

    @JsonCreator(mode = DELEGATING)
    public static CompanionStyle from(String raw) {
        if (raw == null) throw SurveyException.from(INVALID_SURVEY_COMPANIONSTYLE);
        String v = raw.trim();

        for (CompanionStyle e : values()) {
            if (e.name().equalsIgnoreCase(v)) return e;
            if (e.label.equals(v)) return e;
        }
        throw SurveyException.from(INVALID_SURVEY_COMPANIONSTYLE);
    }
}

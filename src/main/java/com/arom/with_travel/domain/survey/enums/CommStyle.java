package com.arom.with_travel.domain.survey.enums;

import com.arom.with_travel.domain.survey.error.SurveyException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;

import static com.arom.with_travel.domain.survey.error.SurveyErrorCode.*;

@AllArgsConstructor
public enum CommStyle implements SurveyEnum {

    TALKATIVE("수다쟁이"),
    QUIET("조용한편"),
    REACTION_KING("리액션킹"),
    RESPECT_ME_TIME("개인시간존중");

    private final String label;

    @Override public String getLabel(){return label;}

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static CommStyle from(String raw) {
        if (raw == null) throw SurveyException.from(INVALID_SURVEY_COMMSTYLE);
        String v = raw.trim();
        for (CommStyle e : values()) {
            if (e.name().equalsIgnoreCase(v)) return e;
            if (e.label.equals(v)) return e;
        }
        throw SurveyException.from(INVALID_SURVEY_COMMSTYLE);
    }
}

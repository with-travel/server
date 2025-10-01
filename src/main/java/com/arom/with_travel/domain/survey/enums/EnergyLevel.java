package com.arom.with_travel.domain.survey.enums;

import com.arom.with_travel.domain.survey.error.SurveyException;
import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import static com.arom.with_travel.domain.survey.error.SurveyErrorCode.INVALID_SURVEY_ENERGYLEVEL;
import static com.fasterxml.jackson.annotation.JsonCreator.Mode.DELEGATING;

@AllArgsConstructor
public enum EnergyLevel implements SurveyEnum {

    MORNING_PERSON("아침형인간"),
    NIGHT_OWL      ("밤올빼미"),
    ENERGIZER      ("에너자이저"),
    HEALING_MODE   ( "힐링모드");

    private final String label;

    @Override public String getLabel() { return label; }


    @JsonCreator(mode = DELEGATING)
    public static EnergyLevel from(String raw) {
        if (raw == null) throw SurveyException.from(INVALID_SURVEY_ENERGYLEVEL);
        String v = raw.trim();

        for (EnergyLevel e : values()) {
            if (e.name().equalsIgnoreCase(v)) return e;
            if (e.label.equals(v)) return e;
        }
        throw SurveyException.from(INVALID_SURVEY_ENERGYLEVEL);
    }
}
package com.arom.with_travel.domain.survey.enums;

import com.arom.with_travel.domain.survey.error.SurveyException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import static com.arom.with_travel.domain.survey.error.SurveyErrorCode.INVALID_SURVEY_ENERGYLEVEL;

@AllArgsConstructor
public enum EnergyLevel implements SurveyEnum {

    MORNING_PERSON("MORNING_PERSON", "아침형인간"),
    NIGHT_OWL      ("NIGHT_OWL",      "밤올빼미"),
    ENERGIZER      ("ENERGIZER",      "에너자이저"),
    HEALING_MODE   ("HEALING_MODE",   "힐링모드");

    private final String code;
    private final String label;

    @Override public String getCode()  { return code; }
    @Override public String getLabel() { return label; }

    /** 응답은 code로 통일 */
    @JsonValue
    public String json() { return code; }

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static EnergyLevel from(String raw) {
        if (raw == null) throw SurveyException.from(INVALID_SURVEY_ENERGYLEVEL);
        String v = raw.trim();

        for (EnergyLevel e : values()) {
            if (e.name().equalsIgnoreCase(v) || e.code.equalsIgnoreCase(v)) return e;
            if (e.label.equals(v)) return e;
            if (e.label.substring(1).equals(v)) return e;
        }
        throw SurveyException.from(INVALID_SURVEY_ENERGYLEVEL);
    }
}
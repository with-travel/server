package com.arom.with_travel.domain.survey.enums;

import com.arom.with_travel.domain.survey.error.SurveyException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;

import static com.arom.with_travel.domain.survey.error.SurveyErrorCode.INVALID_SURVEY_TRAVELPACE;

@AllArgsConstructor
public enum TravelPace implements SurveyEnum {

    TIGHT_SCHEDULE("TIGHT_SCHEDULE", "타이트스케줄"),
    RELAXED("RELAXED", "여유만만"),
    SPONTANEOUS("SPONTANEOUS", "즉흥여행"),
    PLAN_B_READY("PLAN_B_READY", "플랜B준비완료");

    private final String code;
    private final String label;

    @Override public String getCode(){return code;}
    @Override public String getLabel(){return label;}

    @JsonValue
    public String json(){return code;}

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static TravelPace from(String raw) {
        if (raw == null) throw SurveyException.from(INVALID_SURVEY_TRAVELPACE);
        String v = raw.trim();

        for (TravelPace e : values()) {
            // 영문 name/code
            if (e.name().equalsIgnoreCase(v) || e.code.equalsIgnoreCase(v)) return e;
            // 라벨(해시 포함/미포함)
            if (e.label.equals(v)) return e;
            if (e.label.substring(1).equals(v)) return e;
        }
        throw SurveyException.from(INVALID_SURVEY_TRAVELPACE);
    }
}

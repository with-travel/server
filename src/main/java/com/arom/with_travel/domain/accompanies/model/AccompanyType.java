package com.arom.with_travel.domain.accompanies.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum AccompanyType {
    LUNCH("점심"),
    DINNER("저녁"),
    TOTAL("전체");

    @JsonValue private final String type;

    @JsonCreator
    public static AccompanyType fromContinent(String val) {
        return Arrays.stream(values())
                .filter(type -> type.getType().equals(val))
                .findAny()
                .orElse(null);
    }
}

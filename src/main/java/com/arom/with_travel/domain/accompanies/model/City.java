package com.arom.with_travel.domain.accompanies.model;

import com.arom.with_travel.domain.accompanies.error.AccompanyException;
import com.arom.with_travel.global.exception.BaseException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

import static com.arom.with_travel.domain.accompanies.error.AccompanyErrorCode.ACCOMPANY_POST_ERROR;

@Getter
@AllArgsConstructor
public enum City {
    TOKYO("도쿄"),
    OSAKA("오사카"),
    KYOTO("교토")
    ;

    @JsonValue private final String name;

    @JsonCreator
    public static City fromCity(String val) {
        return Arrays.stream(values())
                .filter(type -> type.getName().equals(val))
                .findAny()
                .orElseThrow(() -> AccompanyException.from(ACCOMPANY_POST_ERROR));
    }
}

package com.arom.with_travel.domain.accompanies.model;

import com.arom.with_travel.domain.accompanies.error.AccompanyErrorCode;
import com.arom.with_travel.global.exception.BaseException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;


@Getter
@AllArgsConstructor
public enum Country {
    JAPAN("일본")
    ;
    @JsonValue private final String name;

    @JsonCreator
    public static Country fromCountry(String val) {
        return Arrays.stream(values())
                .filter(type -> type.getName().equals(val))
                .findAny()
                .orElseThrow(() -> BaseException.from(AccompanyErrorCode.ACCOMPANY_POST_ERROR));
    }
}

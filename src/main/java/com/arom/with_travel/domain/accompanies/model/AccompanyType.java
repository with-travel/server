package com.arom.with_travel.domain.accompanies.model;

import com.arom.with_travel.global.exception.BaseException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

import static com.arom.with_travel.global.exception.error.ErrorCode.ACCOMPANY_POST_ERROR;

@Getter
@AllArgsConstructor
public enum AccompanyType {
    LUNCH("점심"),
    DINNER("저녁"),
    TOTAL("전체"),
    TRAVEL("여행"),
    FOOD("식도락"),
    DRIVE("드라이브"),
    SIGHTSEEING("관광"),
    EVENT("행사"),
    CULTURE("문화체험")
    ;

    @JsonValue private final String type;

    @JsonCreator
    public static AccompanyType fromAccompanyType(String val) {
        return Arrays.stream(values())
                .filter(type -> type.getType().equals(val))
                .findAny()
                .orElseThrow(() -> BaseException.from(ACCOMPANY_POST_ERROR));
    }
}

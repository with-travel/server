package com.arom.with_travel.domain.survey.error;

import com.arom.with_travel.global.exception.error.BaseCode;
import com.arom.with_travel.global.exception.error.ErrorDisplayType;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum SurveyErrorCode implements BaseCode {

    SURVEY_NOT_FOUND(HttpStatus.NOT_FOUND,"SVY-0000", "해당 설문이 존재하지 않습니다.", ErrorDisplayType.POPUP),
    ;

    private final HttpStatus status;
    private final String code;
    private final String message;
    private final ErrorDisplayType displayType;
}

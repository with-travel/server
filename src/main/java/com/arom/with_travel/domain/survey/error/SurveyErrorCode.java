package com.arom.with_travel.domain.survey.error;

import com.arom.with_travel.global.exception.error.BaseCode;
import com.arom.with_travel.global.exception.error.ErrorDisplayType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum SurveyErrorCode implements BaseCode {
    // Survey
    SURVEY_NOT_FOUND(HttpStatus.NOT_FOUND, "SVY-0000", "해당 설문이 존재하지 않습니다.", ErrorDisplayType.POPUP),
    INVALID_SURVEY_ANSWER(HttpStatus.BAD_REQUEST, "SVY-0001", "설문 답변이 비어있습니다.", ErrorDisplayType.POPUP),
    OVER_ANSWER_LIMIT(HttpStatus.BAD_REQUEST, "SVY-0002", "답변 개수가 초과되었습니다..", ErrorDisplayType.POPUP),
    INVALID_SURVEY_QUESTION(HttpStatus.BAD_REQUEST, "SVY-0003", "설문 질문이 비어있습니다.", ErrorDisplayType.POPUP),
    INVALID_SURVEY_ENERGYLEVEL(HttpStatus.BAD_REQUEST, "SVY-0004", "에너지 레벨 설문 답변이 비어있습니다.", ErrorDisplayType.POPUP),
    INVALID_SURVEY_TRAVELGOAL(HttpStatus.BAD_REQUEST, "SVY-0005", "여행 목적 설문 답변이 비어있습니다.", ErrorDisplayType.POPUP),
    INVALID_SURVEY_TRAVELPACE(HttpStatus.BAD_REQUEST, "SVY-0006", "여행 페이스 설문 답변이 비어있습니다.", ErrorDisplayType.POPUP),
    INVALID_SURVEY_COMMSTYLE(HttpStatus.BAD_REQUEST, "SVY-0007", "소통 스타일 설문 답변이 비어있습니다.", ErrorDisplayType.POPUP),
    INVALID_SURVEY_RECORDTENDENCY(HttpStatus.BAD_REQUEST, "SVY-0008", "기록 성향 설문 답변이 비어있습니다.", ErrorDisplayType.POPUP),
    INVALID_SURVEY_COMPANIONSTYLE(HttpStatus.BAD_REQUEST, "SVY-0009", "동행 스타일 설문 답변이 비어있습니다.", ErrorDisplayType.POPUP),
    INVALID_SURVEY_SPENDPATTERN(HttpStatus.BAD_REQUEST, "SVY-00010", "소비 패턴 설문 답변이 비어있습니다.", ErrorDisplayType.POPUP),
    ;
    private final HttpStatus status;
    private final String code;
    private final String message;
    private final ErrorDisplayType displayType;
}

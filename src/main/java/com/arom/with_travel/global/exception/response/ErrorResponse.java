package com.arom.with_travel.global.exception.response;

import com.arom.with_travel.global.exception.BaseException;
import com.arom.with_travel.global.exception.error.ErrorCode;
import com.arom.with_travel.global.exception.error.ErrorDisplayType;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;


@Getter
@AllArgsConstructor
@JsonPropertyOrder({"code", "message", "displayType"})
@Builder
public class ErrorResponse {

    @JsonProperty("code")
    private final String code;
    private final String message;
    private final ErrorDisplayType displayType;

    public static ErrorResponse generateFrom(ErrorCode errorCode) {
        return new ErrorResponse(errorCode.getCode(), errorCode.getMessage(), errorCode.getDisplayType());
    }

    public static ErrorResponse generateWithCustomMessage(ErrorCode errorCode, String customMessage) {
        return new ErrorResponse(errorCode.getCode(), customMessage, errorCode.getDisplayType());
    }

    public static ErrorResponse generateFrom(BaseException baseException){
        if(baseException.hasCustomMessage()) {
            return new ErrorResponse(
                    baseException.getBaseCode().getCode(),
                    baseException.getCustomErrorMessage(),
                    baseException.getBaseCode().getDisplayType());
        }
        return new ErrorResponse(
                baseException.getBaseCode().getCode(),
                baseException.getBaseCode().getMessage(),
                baseException.getBaseCode().getDisplayType());
    }
}

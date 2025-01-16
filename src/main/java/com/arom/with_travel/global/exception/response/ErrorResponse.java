package com.arom.with_travel.global.exception.response;

import com.arom.with_travel.global.exception.BaseException;
import com.arom.with_travel.global.exception.error.ErrorDisplayType;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
@JsonPropertyOrder({"code", "message", "displayType"})
public class ErrorResponse {

    @JsonProperty("code")
    private final String code;
    private final String message;
    private final ErrorDisplayType displayType;

    public static ErrorResponse generateErrorResponse(BaseException baseException){
        if(baseException.hasCustomMessage()) {
            return new ErrorResponse(
                    baseException.getErrorCode().getCode(),
                    baseException.getCustomErrorMessage(),
                    baseException.getErrorCode().getDisplayType());
        }
        return new ErrorResponse(
                baseException.getErrorCode().getCode(),
                baseException.getErrorCode().getMessage(),
                baseException.getErrorCode().getDisplayType());
    }
}

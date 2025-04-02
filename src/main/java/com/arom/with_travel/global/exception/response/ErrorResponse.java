package com.arom.with_travel.global.exception.response;

import com.arom.with_travel.global.exception.BaseException;
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

    public static ErrorResponse generateErrorResponse(BaseException baseException){
        if(baseException.hasCustomMessage()) {
            return new ErrorResponse(
                    baseException.getCode().getCode(),
                    baseException.getCustomErrorMessage(),
                    baseException.getCode().getDisplayType());
        }
        return new ErrorResponse(
                baseException.getCode().getCode(),
                baseException.getCode().getMessage(),
                baseException.getCode().getDisplayType());
    }
}

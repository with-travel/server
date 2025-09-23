package com.arom.with_travel.global.exception;

import com.arom.with_travel.global.exception.error.BaseCode;
import com.arom.with_travel.global.exception.error.ErrorCode;
import lombok.Getter;

@Getter
public class BaseException extends RuntimeException {

    private final BaseCode baseCode;
    private String customErrorMessage;

    protected BaseException(BaseCode code) {
        this.baseCode = code;
    }

    private BaseException(BaseCode code, final String message) {
        this.baseCode = code;
        this.customErrorMessage = message;
    }

    public boolean hasCustomMessage(){
        return customErrorMessage != null;
    }

    public static BaseException from(BaseCode baseCode) {
        return new BaseException(baseCode);
    }

    public static BaseException from(BaseCode baseCode, final String customErrorMessage){
        return new BaseException(baseCode, customErrorMessage);
    }
}

package com.arom.with_travel.global.exception;

import com.arom.with_travel.global.exception.error.BaseCode;
import com.arom.with_travel.global.exception.error.ErrorCode;
import lombok.Getter;

@Getter
public class BaseException extends RuntimeException {

    private final BaseCode code;
    private String customErrorMessage;

    private BaseException(BaseCode code) {
        this.code = code;
    }

    private BaseException(BaseCode code, final String message) {
        this.code = code;
        this.customErrorMessage = message;
    }

    public boolean hasCustomMessage(){
        return customErrorMessage != null;
    }

    public static BaseException from(BaseCode code) {
        return new BaseException(code);
    }

    public static BaseException from(BaseCode code, final String customErrorMessage){
        return new BaseException(code, customErrorMessage);
    }
}

package com.arom.with_travel.global.security.error;

import com.arom.with_travel.global.exception.BaseException;
import com.arom.with_travel.global.exception.error.BaseCode;

public class AuthException extends BaseException {
    protected AuthException(BaseCode code) {
        super(code);
    }
}

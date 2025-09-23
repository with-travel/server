package com.arom.with_travel.domain.accompanies.error;

import com.arom.with_travel.global.exception.BaseException;
import com.arom.with_travel.global.exception.error.BaseCode;
import com.arom.with_travel.global.exception.error.ErrorCode;

public class AccompanyException extends BaseException {
    public AccompanyException(BaseCode code) {
        super(code);
    }
}

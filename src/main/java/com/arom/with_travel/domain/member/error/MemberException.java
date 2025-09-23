package com.arom.with_travel.domain.member.error;

import com.arom.with_travel.global.exception.BaseException;
import com.arom.with_travel.global.exception.error.BaseCode;

public class MemberException extends BaseException {
    protected MemberException(BaseCode code) {
        super(code);
    }
}

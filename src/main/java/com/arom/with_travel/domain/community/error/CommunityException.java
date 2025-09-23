package com.arom.with_travel.domain.community.error;

import com.arom.with_travel.global.exception.BaseException;
import com.arom.with_travel.global.exception.error.BaseCode;

public class CommunityException extends BaseException {
    protected CommunityException(BaseCode code) {
        super(code);
    }
}

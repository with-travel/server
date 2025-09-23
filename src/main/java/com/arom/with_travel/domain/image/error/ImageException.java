package com.arom.with_travel.domain.image.error;

import com.arom.with_travel.global.exception.BaseException;
import com.arom.with_travel.global.exception.error.BaseCode;

public class ImageException extends BaseException {
    protected ImageException(BaseCode code) {
        super(code);
    }
}

package com.arom.with_travel.domain.survey.error;

import com.arom.with_travel.global.exception.BaseException;
import com.arom.with_travel.global.exception.error.BaseCode;

public class SurveyException extends BaseException {
    protected SurveyException(BaseCode code) {
        super(code);
    }
}

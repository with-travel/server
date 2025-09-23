package com.arom.with_travel.domain.community_reply.error;

import com.arom.with_travel.global.exception.BaseException;
import com.arom.with_travel.global.exception.error.BaseCode;

public class CommunityReplyException extends BaseException {
    protected CommunityReplyException(BaseCode code) {
        super(code);
    }
}

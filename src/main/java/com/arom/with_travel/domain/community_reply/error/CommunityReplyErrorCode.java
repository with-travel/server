package com.arom.with_travel.domain.community_reply.error;

import com.arom.with_travel.global.exception.error.BaseCode;
import com.arom.with_travel.global.exception.error.ErrorDisplayType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum CommunityReplyErrorCode implements BaseCode {

    REPLY_NOT_FOUND(HttpStatus.NOT_FOUND, "REP-0000", "해당 댓글이 존재하지 않습니다.", ErrorDisplayType.POPUP),
    REPLY_FORBIDDEN(HttpStatus.FORBIDDEN, "REP-0001", "해당 댓글에 수정 및 삭제 권한이 없습니다.", ErrorDisplayType.POPUP),
    ;

    private final HttpStatus status;
    private final String code;
    private final String message;
    private final ErrorDisplayType displayType;
}

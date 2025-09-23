package com.arom.with_travel.domain.community.error;

import com.arom.with_travel.global.exception.error.BaseCode;
import com.arom.with_travel.global.exception.error.ErrorDisplayType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum CommunityErrorCode implements BaseCode {
    CONTINENT_NOT_FOUND(HttpStatus.NOT_FOUND, "COM-0000", "해당 대륙이 존재하지 않습니다.", ErrorDisplayType.POPUP),
    COMMUNITY_NOT_FOUND(HttpStatus.NOT_FOUND, "CON-0001", "해당 커뮤니티 게시글이 존재하지 않습니다.", ErrorDisplayType.POPUP),
    COMMUNITY_FORBIDDEN(HttpStatus.FORBIDDEN, "CON-0002", "해당 게시글의 수정 및 삭제 권한이 없습니다.", ErrorDisplayType.POPUP),
    ;
    private final HttpStatus status;
    private final String code;
    private final String message;
    private final ErrorDisplayType displayType;
}

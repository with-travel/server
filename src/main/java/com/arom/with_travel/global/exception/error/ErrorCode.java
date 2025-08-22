package com.arom.with_travel.global.exception.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    // Common Error
    INTERNAL_SERVER_ERROR("C001", "Server Error", ErrorDisplayType.POPUP),
    INVALID_INPUT_VALUE("C002", "Invalid Input Value",ErrorDisplayType.POPUP ),
    METHOD_NOT_ALLOWED("C003", "Invalid HTTP Method", ErrorDisplayType.POPUP),
    ENTITY_NOT_FOUND("C004", "Entity Not Found", ErrorDisplayType.POPUP ),
    CONFLICT("C005", "Conflict Occurred", ErrorDisplayType.POPUP),
    UNACCEPTABLE_EXTENSION("C007", "Unacceptable Extension",ErrorDisplayType.POPUP ),
    INVALID_JSON_FORMAT("C008", "Invalid JSON Format",ErrorDisplayType.POPUP ),
    MISSING_PARAMETER("C009", "Missing Parameter",ErrorDisplayType.POPUP ),
    INVALID_PARAMETER_TYPE("C010", "Invalid Parameter Type",ErrorDisplayType.POPUP ),
    MISSING_PATH_VARIABLE("C011", "Missing Path Variable", ErrorDisplayType.POPUP),
    FORBIDDEN("C012", "Forbidden", ErrorDisplayType.POPUP),
    ERR_DATA_INTEGRITY_VIOLATION("E001", "Data integrity violation", ErrorDisplayType.POPUP),
    VALIDATION_FAILED("C013", "Validation Failed", ErrorDisplayType.POPUP),

    INVALID_TOKEN("TOKEN-0001", "유효하지 않는 토큰입니다.", ErrorDisplayType.POPUP),
    EXPIRED_ACCESS_TOKEN("TOKEN-0000", "토큰 오류", ErrorDisplayType.POPUP),
    INVALID_EMAIL_OR_PASSWORD("MEMBER-0001", "유효하지 않는 이메일, 비번", ErrorDisplayType.POPUP),
    EMPTY_TOKEN_PROVIDED("TOKEN-0002", "토큰 텅텅", ErrorDisplayType.POPUP),

    // Validation
    REQ_BODY_ERROR("REQ-0000", "", ErrorDisplayType.POPUP),
    REQ_PARAMS_ERROR("REQ-0001", "", ErrorDisplayType.POPUP),

    //member
    MEMBER_NOT_FOUND("MEM-0000", "해당 회원이 존재하지 않습니다.", ErrorDisplayType.POPUP),
    MEMBER_ALREADY_REGISTERED("MEM-0001", "이미 회원가입되어 있습니다.", ErrorDisplayType.POPUP),

    //token
    TOKEN_NOT_FOUND("TKN-0000", "refresh token이 존재하지 않습니다.", ErrorDisplayType.POPUP),

    //accompany
    ACCOMPANY_NOT_FOUND("ACC-0000", "해당 동행이 존재하지 않습니다.", ErrorDisplayType.POPUP),
    ACCOMPANY_ALREADY_APPLIED("ACC-0001", "이미 신청한 동행입니다.", ErrorDisplayType.POPUP),
    ACCOMPANY_POST_ERROR("ACC-0002", "동행 입력이 올바르지 않습니다.", ErrorDisplayType.POPUP),
    ACCOMPANY_ALREADY_LIKED("ACC-0003", "좋아요를 이미 눌렀습니다.", ErrorDisplayType.POPUP),
    ACCOMPANY_COMMENT_NOT_FOUND("ACC-0004", "해당 동행 댓글을 찾을 수 없습니다.", ErrorDisplayType.POPUP),
    ACCOMPANY_COMMENT_NO_PERMISSION_UPDATE("ACC-0005", "댓글을 수정할 수 없습니다.", ErrorDisplayType.POPUP),

    // ACCOMPANY_ALREADY_CONFIRMED("ACC-0002", "참가 확정된 동행입니다.", ErrorDisplayType.POPUP)
    ACCOMPANY_LIKES_UNABLE_DECREASE("ACC-0006", "좋아요 수가 0보다 작습니다.", ErrorDisplayType.POPUP),

    //survey
    SURVEY_NOT_FOUND("SVY-0000", "해당 설문이 존재하지 않습니다.", ErrorDisplayType.POPUP),
    INVALID_SURVEY_ANSWER("SVY-0001", "설문 답변이 비어있습니다.", ErrorDisplayType.POPUP),
    OVER_ANSWER_LIMIT("SVY-0002", "답변 개수가 초과되었습니다..", ErrorDisplayType.POPUP),
    INVALID_SURVEY_QUESTION("SVY-0003", "설문 질문이 비어있습니다.", ErrorDisplayType.POPUP),

    // image
    INVALID_IMG_TYPE("IMG-0000", "지원하지 않는 이미지 형식입니다.", ErrorDisplayType.POPUP),
    IMG_URL_MUST_FILLED("IMG-0001", "이미지 url이 존재해야합니다.", ErrorDisplayType.POPUP),

    // community
    CONTINENT_NOT_FOUND("COM-0000", "해당 대륙이 존재하지 않습니다.", ErrorDisplayType.POPUP),
    COMMUNITY_NOT_FOUND("CON-0001", "해당 커뮤니티 게시글이 존재하지 않습니다.", ErrorDisplayType.POPUP),
    COMMUNITY_FORBIDDEN("CON-0002", "해당 게시글의 수정 및 삭제 권한이 없습니다.", ErrorDisplayType.POPUP),

    // reply
    REPLY_NOT_FOUND("REP-0000", "해당 댓글이 존재하지 않습니다.", ErrorDisplayType.POPUP),
    REPLY_FORBIDDEN("REP-0001", "해당 댓글에 수정 및 삭제 권한이 없습니다.", ErrorDisplayType.POPUP)
    ;

    private final String code;
    private final String message;
    private final ErrorDisplayType displayType;
}

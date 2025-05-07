package com.arom.with_travel.domain.member.swagger;

import com.arom.with_travel.domain.member.dto.MemberSignupResponseDto;
import com.arom.with_travel.global.exception.response.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.MediaType;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@Operation(summary = "회원 정보 등록", description = "신규 회원의 추가 정보를 저장하고 홈 화면으로 리다이렉션 합니다.")
@ApiResponses(value = {
        @ApiResponse(responseCode = "302", description = "회원 정보 등록 후 홈 화면으로 리다이렉션",
                content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                        schema = @Schema(implementation = MemberSignupResponseDto.class))),
        @ApiResponse(responseCode = "404", description = "회원 정보를 찾을 수 없음",
                content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                        schema = @Schema(implementation = ErrorResponse.class)))
})
public @interface PostMemberSignup {
}

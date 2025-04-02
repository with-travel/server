package com.arom.with_travel.domain.survey.swagger;

import com.arom.with_travel.domain.survey.dto.response.SurveyResponseDto;
import com.arom.with_travel.global.exception.response.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.MediaType;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@Operation(
        summary = "내 설문 목록 조회",
        description = "현재 로그인한 사용자의 모든 설문 답변을 조회합니다."
)
@ApiResponses(value = {
        @ApiResponse(
                responseCode = "200",
                description = "내 설문 목록 조회 성공",
                content = @Content(
                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                        array = @ArraySchema(
                                schema = @Schema(implementation = SurveyResponseDto.class)
                        )
                )
        ),
        @ApiResponse(
                responseCode = "4xx",
                description = "설문 조회 요청 처리 실패",
                content = @Content(
                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                        schema = @Schema(implementation = ErrorResponse.class)
                )
        )
})
public @interface GetMySurveys {
}
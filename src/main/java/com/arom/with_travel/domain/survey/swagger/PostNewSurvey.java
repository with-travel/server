package com.arom.with_travel.domain.survey.swagger;

import com.arom.with_travel.domain.survey.dto.response.SurveyResponseDto;
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
@Operation(
        summary = "새로운 설문조사 생성",
        description = "새로운 설문조사를 생성하여 DB에 저장합니다."
)
@ApiResponses(value = {
        @ApiResponse(
                responseCode = "200",
                description = "설문 생성 성공",
                content = @Content(
                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                        schema = @Schema(implementation = SurveyResponseDto.class)
                )
        ),
        @ApiResponse(
                responseCode = "4xx",
                description = "설문 생성 요청 처리 실패",
                content = @Content(
                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                        schema = @Schema(implementation = ErrorResponse.class)
                )
        )
})
public @interface PostNewSurvey {
}

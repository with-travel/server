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
        summary = "설문 단건 조회",
        description = "surveyId를 Path로 받아 해당 설문 내용을 조회합니다."
)
@ApiResponses(value = {
        @ApiResponse(
                responseCode = "200",
                description = "설문 조회 성공",
                content = @Content(
                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                        schema = @Schema(implementation = SurveyResponseDto.class)
                )
        ),
        @ApiResponse(
                responseCode = "404",
                description = "대상 설문을 찾을 수 없음",
                content = @Content(
                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                        schema = @Schema(implementation = ErrorResponse.class)
                )
        )
})
public @interface GetSingleSurvey {
}

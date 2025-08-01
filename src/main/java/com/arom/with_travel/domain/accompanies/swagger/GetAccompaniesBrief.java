package com.arom.with_travel.domain.accompanies.swagger;

import com.arom.with_travel.domain.accompanies.dto.response.AccompanyBriefResponse;
import com.arom.with_travel.global.exception.response.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.MediaType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Operation(
        summary     = "동행 목록 간단 조회, 동행 첫 화면에 사용됨",
        description = """
            동행 목록 첫 페이지는 어느 국가를 기반으로
            국가(country) 필터와 커서(lastId) 기반으로
            동행 목록을 Slice 방식(무한 스크롤)에 맞춰 조회합니다.
            """
)
@Parameter(
        name        = "country",
        in          = ParameterIn.QUERY,
        required    = false,
        description = "국가 (예: 일본)",
        example     = "KOREA"
)
@Parameter(
        name        = "lastId",
        in          = ParameterIn.QUERY,
        required    = false,
        description = "마지막으로 조회된 동행 ID (커서)",
        example     = "42"
)
@Parameter(
        name        = "size",
        in          = ParameterIn.QUERY,
        required    = false,
        description = "한 번에 가져올 데이터 개수",
        schema      = @Schema(defaultValue = "10")
)
@ApiResponses(value = {
        @ApiResponse(
                responseCode = "200",
                description  = "동행 목록 조회 성공",
                content      = @Content(
                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                        array     = @ArraySchema(schema = @Schema(implementation = AccompanyBriefResponse.class))
                )
        ),
        @ApiResponse(
                responseCode = "400",
                description  = "잘못된 요청 파라미터",
                content      = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                        schema = @Schema(implementation = ErrorResponse.class))
        )
})
public @interface GetAccompaniesBrief {}
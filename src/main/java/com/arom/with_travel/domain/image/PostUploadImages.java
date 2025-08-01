package com.arom.with_travel.domain.image;

import com.arom.with_travel.domain.image.dto.UploadedImageResponse;
import com.arom.with_travel.global.exception.response.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
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

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Operation(
        summary = "이미지 업로드",
        description = """
                Multipart/form-data 요청으로 여러 이미지를 S3에 업로드합니다.
                저장 디렉터리(dir) 파라미터는 S3 버킷 내의 하위 경로를 의미하며,
                정상 처리되면 업로드된 각 이미지의 URL과 원본 파일명을 담은 리스트를 반환합니다.
                """)
@Parameters({
        @Parameter(
                name = "files",
                description = "업로드할 이미지 파일들 (복수 가능)",
                in = ParameterIn.DEFAULT,
                required = true,
                content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE,
                        array = @ArraySchema(schema = @Schema(type = "string", format = "binary")))
        ),
        @Parameter(
                name = "dir",
                description = """
                        S3 저장 디렉터리
                        디렉토리 종류 :
                        accompany-img(동행글),
                        member-profile-img(프로필 이미지),
                        community-img(커뮤니티 글)
                        """,
                in = ParameterIn.QUERY,
                required = true,
                schema = @Schema(type = "string")
        )
})
@ApiResponses(value = {
        @ApiResponse(
                responseCode = "200",
                description = "업로드 성공",
                content = @Content(
                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                        array = @ArraySchema(schema = @Schema(implementation = UploadedImageResponse.class)))
        ),
        @ApiResponse(
                responseCode = "400",
                description = "요청 형식 오류(파일 누락 등)",
                content = @Content(
                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                        schema = @Schema(implementation = ErrorResponse.class)))
})
public @interface PostUploadImages {
}
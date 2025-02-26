package com.arom.with_travel.global.jwt.controller;

import com.arom.with_travel.global.jwt.service.RefreshTokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Map;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/token")
public class TokenController {

    private final RefreshTokenService refreshTokenService;

    @Operation(
            summary = "새로운 Access Token 생성",
            description = "Refresh Token을 사용하여 새로운 Access Token을 생성합니다."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "새로운 Access Token 생성 성공",
                    content = @Content(schema = @Schema(implementation = Map.class))
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "인증 실패",
                    content = @Content(schema = @Schema(implementation = Map.class))
            ),
    })
    @PostMapping("/refresh")
    public ResponseEntity<Map<String, String>> refreshAccessToken(HttpServletRequest request) {
        // Refresh Token 쿠키에서 가져오기
        String refreshToken = Arrays.stream(request.getCookies())
                .filter(cookie -> "refresh_token".equals(cookie.getName()))
                .findFirst()
                .map(Cookie::getValue)
                .orElse(null);

        if (refreshToken == null) {
            return ResponseEntity.status(401).body(Map.of("error", "Refresh Token not found"));
        }

        // 새로운 Access Token 발급
        String newAccessToken = refreshTokenService.refreshAccessToken(refreshToken);

        return ResponseEntity.ok(Map.of("access_token", newAccessToken));
    }
}

package com.arom.with_travel.domain.member.controller;

import com.arom.with_travel.domain.member.dto.request.LocalLoginRequest;
import com.arom.with_travel.domain.member.dto.request.SignupWithSurveyRequestDto;
import com.arom.with_travel.domain.member.dto.response.LoginResponse;
import com.arom.with_travel.domain.member.service.LocalAuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final LocalAuthService authService;

    // 이메일 중복 체크
    @GetMapping("/email-available")
    public boolean emailAvailable(@RequestParam String email) {
        return authService.isEmailAvailable(email);
    }

    // 이메일 등록(회원가입)
    @PostMapping("/register")
    public ResponseEntity<LoginResponse> register(@Valid @RequestBody SignupWithSurveyRequestDto req) {
        return ResponseEntity.ok(authService.registerWithSurvey(req));
    }

    // 로그인
    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LocalLoginRequest req) {
        return authService.login(req);
    }
}

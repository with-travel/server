package com.arom.with_travel.domain.member.controller;

import com.arom.with_travel.domain.member.Member;
import com.arom.with_travel.domain.member.dto.MemberSignupRequestDto;
import com.arom.with_travel.domain.member.dto.MemberSignupResponseDto;
import com.arom.with_travel.domain.member.service.MemberSignupService;
import com.arom.with_travel.domain.member.swagger.PostMemberSignup;
import com.arom.with_travel.global.jwt.service.TokenProvider;
import com.arom.with_travel.global.oauth2.dto.CustomOAuth2User;
import com.arom.with_travel.global.oauth2.handler.OAuth2SuccessHandler;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;

@RestController
@RequiredArgsConstructor
@Tag(name = "회원가입", description = "사용자 회원가입 API")
public class MemberSignupController {

    private final MemberSignupService memberSignupService;
    private final TokenProvider tokenProvider;  // 토큰 검증용

    @GetMapping("/signup")
    public ResponseEntity<?> checkUserRole(
            @CookieValue(name = OAuth2SuccessHandler.GUEST_TOKEN_COOKIE, required = false)
            String guestToken,
            HttpServletResponse response
    ) {

        if (guestToken == null) {
            // 토큰 없이 왔으면 401
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
        }

        // 토큰 검증, Authentication 세팅
        Authentication auth = tokenProvider.getAuthentication(guestToken);
        SecurityContextHolder.getContext().setAuthentication(auth);

        Object principal = auth.getPrincipal();
        if (!(principal instanceof CustomOAuth2User)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("잘못된 인증 정보입니다.");
        }

        CustomOAuth2User customUser = (CustomOAuth2User) principal;

        // 기존 회원이면
        boolean exists = memberSignupService.existsByEmail(customUser.getEmail());
        if (exists) {
            Member m = memberSignupService.getByEmail(customUser.getEmail());
            return ResponseEntity.status(HttpStatus.FOUND)
                    .header("Location", "/home/" + m.getId())
                    .build();
        }

        // 신규 GUEST면 추가정보 입력 페이지로
        return ResponseEntity.status(HttpStatus.FOUND)
                .header("Location", "/signup/register")
                .build();
    }

    @GetMapping("/signup/register")
    public String signupRegister() {
        return "forward:/index.html";
    }

    @PostMapping("/signup/register")
    @PostMemberSignup
    public ResponseEntity<?> registerUser(
            @CookieValue(name = OAuth2SuccessHandler.GUEST_TOKEN_COOKIE, required = false)
            String guestToken,
            @AuthenticationPrincipal CustomOAuth2User customUser,
            @RequestBody @Valid MemberSignupRequestDto requestDto,
            HttpServletResponse response
    ) {

        MemberSignupResponseDto savedMember = memberSignupService.registerMember(
                customUser.getEmail(),
                customUser.getOauthId(),
                requestDto
        );

        // 가입 후 user용 JWT 발급
        Member newMember = memberSignupService.getByEmail(customUser.getEmail());
        String accessToken = tokenProvider.generateToken(newMember, Duration.ofDays(1));
        response.setHeader("Authorization", "Bearer " + accessToken);

        return ResponseEntity.status(HttpStatus.FOUND)
                .header("Location", "/home/" + savedMember.getId())
                .build();
    }
}

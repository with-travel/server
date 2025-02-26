package com.arom.with_travel.domain.member.controller;

import com.arom.with_travel.domain.member.Member;
import com.arom.with_travel.domain.member.dto.MemberSignupRequestDto;
import com.arom.with_travel.domain.member.dto.MemberSignupResponseDto;
import com.arom.with_travel.domain.member.service.MemberSignupService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@Tag(name = "회원가입", description = "사용자 회원가입 API")
public class MemberSignupController {
    private final MemberSignupService memberSignupService;

    @PostMapping("/register")
    @Operation(summary = "회원 정보 등록", description = "기존 소셜 회원의 추가 정보를 저장합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "회원 정보 등록 성공"),
            @ApiResponse(responseCode = "404", description = "회원 정보를 찾을 수 없음")
    })
    public MemberSignupResponseDto registerUser(
            @RequestParam String email,
            @RequestBody @Valid MemberSignupRequestDto requestDto) {

        Member member = memberSignupService.registerMember(email, requestDto);
        return MemberSignupResponseDto.from(member);
    }
}

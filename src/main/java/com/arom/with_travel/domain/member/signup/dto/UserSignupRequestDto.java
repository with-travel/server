package com.arom.with_travel.domain.member.signup.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSignupRequestDto {

    @NotBlank(message = "닉네임을 입력해주세요.")
    private String nickname;

    @NotBlank(message = "생년월일을 입력해주세요.")
    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "생년월일은 yyyy-MM-dd 형식이어야 합니다.")
    private String birthdate; // 생년월일 (yyyy-MM-dd)

    @NotBlank(message = "성별을 선택해주세요.")
    private String gender; // "남자", "여자"
}

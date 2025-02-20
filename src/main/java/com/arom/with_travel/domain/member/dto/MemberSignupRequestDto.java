package com.arom.with_travel.domain.member.dto;

import com.arom.with_travel.domain.member.Member.Gender;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Schema(description = "회원가입 요청 DTO")
public class MemberSignupRequestDto {

    @NotBlank(message = "닉네임을 입력해주세요.")
    @Schema(description = "사용자 닉네임", example = "피카츄")
    private String nickname;

    @NotNull(message = "생년월일을 입력해주세요.")
    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "생년월일은 yyyy-MM-dd 형식이어야 합니다.")
    @Schema(description = "사용자 생년월일", example = "2003-05-30")
    private LocalDate birthdate; // 생년월일 (yyyy-MM-dd)

    @NotNull(message = "성별을 선택해주세요.")
    @Schema(description = "사용자 성별 (MALE/FEMALE)", example = "MALE")
    private Gender gender;
}

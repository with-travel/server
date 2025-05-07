package com.arom.with_travel.domain.member.dto;

import com.arom.with_travel.domain.member.Member.Gender;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Schema(description = "사용자 생년월일", example = "2003-05-30")
    private LocalDate birthdate;

    @NotNull(message = "성별을 선택해주세요.")
    @Schema(description = "사용자 성별 (MALE/FEMALE)", example = "MALE")
    private Gender gender;
}

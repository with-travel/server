package com.arom.with_travel.domain.member.dto.request;

import com.arom.with_travel.domain.member.Member.Gender;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "회원가입 요청 DTO")
@ToString
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

    @NotEmpty(message = "자기소개란을 작성해주세요")
    @Schema(description = "짧은 자기소개", example = "안녕하세요")
    private String introduction;

    @NotBlank(message = "이메일을 입력해주세요.")
    @Email(message = "올바른 이메일 형식이 아닙니다.")
    @Schema(description = "로그인용 이메일", example = "pikachu@example.com")
    private String email;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Size(min = 8, max = 64, message = "비밀번호는 8~64자여야 합니다.")
    @Schema(description = "로그인 비밀번호(서버에서 해시 저장)", example = "pika1234!")
    private String password;

    @NotBlank(message = "이름(실명)을 입력해주세요.")
    @Schema(description = "이름(실명)", example = "한지우")
    private String name;

    @NotBlank(message = "전화번호를 입력해주세요.")
    @Pattern(
            regexp = "^01[0-9]-\\d{3,4}-\\d{4}$",
            message = "전화번호 형식이 올바르지 않습니다. (예: 010-1234-5678)"
    )
    @Schema(description = "전화번호", example = "010-1234-5678")
    private String phone;
}

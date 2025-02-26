package com.arom.with_travel.domain.member.dto;

import com.arom.with_travel.domain.member.Member;
import com.arom.with_travel.domain.member.Member.Gender;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@Schema(description = "회원가입 응답 DTO")
public class MemberSignupResponseDto {

    @Schema(description = "회원 ID", example = "1")
    private Long id;

    @Schema(description = "회원 이메일", example = "test@example.com")
    private String email;

    @Schema(description = "회원 닉네임", example = "피카츄")
    private String nickname;

    @Schema(description = "회원 생년월일", example = "2003-05-30")
    private LocalDate birthdate;

    @Schema(description = "회원 성별 (MALE/FEMALE)", example = "MALE")
    private Gender gender;

    public static MemberSignupResponseDto from(Member member) {
        return new MemberSignupResponseDto(
                member.getId(),
                member.getEmail(),
                member.getNickname(),
                member.getBirth(),
                member.getGender()
        );
    }
}

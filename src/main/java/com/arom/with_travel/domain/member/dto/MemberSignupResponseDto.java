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

    @Schema(example = "1")
    private Long id;

    @Schema(example = "test@example.com")
    private String email;

    @Schema(example = "피카츄")
    private String nickname;

    @Schema(example = "2003-05-30")
    private LocalDate birthdate;

    @Schema(example = "MALE")
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

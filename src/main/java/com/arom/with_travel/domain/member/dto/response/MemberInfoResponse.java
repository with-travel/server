package com.arom.with_travel.domain.member.dto.response;

import com.arom.with_travel.domain.member.Member;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class MemberInfoResponse {
    @Schema(example = "1")
    private Long id;

    @Schema(example = "test@example.com")
    private String email;

    @Schema(example = "피카츄")
    private String nickname;

    @Schema(example = "2003-05-30")
    private LocalDate birthdate;

    @Schema(example = "MALE")
    private Member.Gender gender;

    private Boolean infoChecked;

    public static MemberInfoResponse from(Member member) {
        return new MemberInfoResponse(
                member.getId(),
                member.getEmail(),
                member.getNickname(),
                member.getBirth(),
                member.getGender(),
                member.getAdditionalDataChecked()
        );
    }
}

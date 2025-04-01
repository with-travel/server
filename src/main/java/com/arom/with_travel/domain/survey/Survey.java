package com.arom.with_travel.domain.survey;

import com.arom.with_travel.domain.member.Member;
import com.arom.with_travel.global.entity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE survey SET is_deleted = true, deleted_at = now() where id = ?")
@SQLRestriction("is_deleted is FALSE")
public class Survey extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull @Lob private String question;
    @NotNull @Lob private String answer1;
    @NotNull @Lob private String answer2;
    @NotNull @Lob private String answer3;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    public Survey(Member member, String answer1, String answer2, String answer3) {
        this.member = member;
        this.answer1 = answer1;
        this.answer2 = answer2;
        this.answer3 = answer3;
    }
}

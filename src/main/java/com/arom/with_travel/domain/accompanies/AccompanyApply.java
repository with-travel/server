package com.arom.with_travel.domain.accompanies;

import com.arom.with_travel.domain.member.Member;
import com.arom.with_travel.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AccompanyApply extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private ApplyType applyType = ApplyType.APPLY;

    public enum ApplyType{
        APPLY,
        CONFIRM
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accompany_id")
    private Accompanies accompanies;

    // 신청 회원
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private AccompanyApply(Accompanies accompanies, Member member) {
        this.accompanies = accompanies;
        this.member = member;
    }

    public static AccompanyApply apply(Accompanies accompany, Member member){
        return new AccompanyApply(accompany, member);
    }
}

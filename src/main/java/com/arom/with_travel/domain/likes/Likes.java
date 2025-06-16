package com.arom.with_travel.domain.likes;

import com.arom.with_travel.domain.accompanies.model.Accompany;
import com.arom.with_travel.domain.member.Member;
import com.arom.with_travel.domain.shorts.Shorts;
import com.arom.with_travel.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE likes SET is_deleted = true, deleted_at = now() where id = ?")
@SQLRestriction("is_deleted is FALSE")
public class Likes extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shorts_id")
    private Shorts shorts;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accompanies_id")
    private Accompany accompany;

    @Builder
    public Likes(Member member, Accompany accompany) {
        this.member = member;
        this.accompany = accompany;
        member.getLikes().add(this);
    }

    @Builder
    public Likes(Member member, Shorts shorts) {
        this.member = member;
        this.shorts = shorts;
    }

    public static Likes create(Member member, Accompany accompany){
        return new Likes(member, accompany);
    }
}

package com.arom.with_travel.domain.community;

import com.arom.with_travel.domain.community_reply.CommunityReply;
import com.arom.with_travel.domain.image.Image;
import com.arom.with_travel.domain.member.Member;
import com.arom.with_travel.global.entity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE community SET is_deleted = true, deleted_at = now() where id = ?")
@SQLRestriction("is_deleted is FALSE")
public class Community extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String title;

    @NotNull
    private String content;

    @NotNull
    private String continent;

    @NotNull
    private String country;

    @NotNull
    private String city;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "community")
    private List<CommunityReply> communityReplies = new ArrayList<>();

    @OneToMany(mappedBy = "community")
    private List<Image> images = new ArrayList<>();
}

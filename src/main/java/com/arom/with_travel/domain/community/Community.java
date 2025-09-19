package com.arom.with_travel.domain.community;

import com.arom.with_travel.domain.community.enums.CommunityTag;
import com.arom.with_travel.domain.community_reply.CommunityReply;
import com.arom.with_travel.domain.image.Image;
import com.arom.with_travel.domain.member.Member;
import com.arom.with_travel.global.entity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
@SQLDelete(sql = "UPDATE community SET is_deleted = true, deleted_at = now() WHERE id = ?")
@SQLRestriction("is_deleted = FALSE")
@Table(indexes = {
        @Index(name = "idx_community_tag", columnList = "tag"),
        @Index(name = "idx_community_created_at", columnList = "created_at"),
        @Index(name = "idx_community_like_count", columnList = "like_count"),
        @Index(name = "idx_community_view_count", columnList = "view_count")
})
public class Community extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull @Column(length = 120)
    private String title;

    @NotNull @Lob
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false)
    private CommunityTag tag;

    @NotNull private String continent;
    @NotNull private String country;
    @NotNull private String city;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @OneToMany(mappedBy = "community", orphanRemoval = true)
    private List<CommunityReply> communityReplies = new ArrayList<>();

    @OneToMany(mappedBy = "community", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Image> images = new ArrayList<>();

    @Column(name = "view_count", nullable = false)
    private long viewCount = 0L;

    @Column(name = "like_count", nullable = false)
    private long likeCount = 0L;

    @Column(name = "reply_count", nullable = false)
    private long replyCount = 0L;

    public static Community create(Member writer, String title, String content,
                                   CommunityTag tag,
                                   String continent, String country, String city) {
        Community c = Community.builder()
                .member(writer)
                .title(title)
                .content(content)
                .tag(tag)
                .continent(continent)
                .country(country)
                .city(city)
                .build();
        c.changeMember(writer);
        return c;
    }

    public void changeMember(Member newMember) {
        if (this.member != null) {
            this.member.getCommunities().remove(this);
        }
        this.member = newMember;
        if (newMember != null && !newMember.getCommunities().contains(this)) {
            newMember.getCommunities().add(this);
        }
    }

    public void addReply(CommunityReply reply) {
        this.communityReplies.add(reply);
        if (reply.getCommunity() != this) {
            reply.setCommunity(this);
        }
    }

    public void addImage(Image image) {
        images.add(image);
        image.attachToCommunity(this);
    }

    public void update(String title, String content, CommunityTag tag,
                       String continent, String country, String city) {
        this.title = title;
        this.content = content;
        this.tag = tag;
        this.continent = continent;
        this.country = country;
        this.city = city;
    }
}

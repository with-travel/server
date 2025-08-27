package com.arom.with_travel.domain.community;

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
@SQLDelete(sql = "UPDATE community SET is_deleted = true, deleted_at = now() where id = ?")
@SQLRestriction("is_deleted is FALSE")
public class Community extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull @Column(length = 120)
    private String title;

    @NotNull @Lob
    private String content;

    @NotNull private String continent;
    @NotNull private String country;
    @NotNull private String city;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @OneToMany(mappedBy = "community")
    private List<CommunityReply> communityReplies = new ArrayList<>();

    @OneToMany(mappedBy = "community")
    private List<Image> images = new ArrayList<>();

    @Column(nullable = false)
    private long viewCount = 0L;

    public static Community create(Member writer, String title, String content,
                                   String continent, String country, String city) {
        Community c = Community.builder()
                .member(writer)
                .title(title)
                .content(content)
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


    public void update(String title, String content, String continent, String country, String city) {
        this.title = title;
        this.content = content;
        this.continent = continent;
        this.country = country;
        this.city = city;
    }
}

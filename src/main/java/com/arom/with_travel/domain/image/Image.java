package com.arom.with_travel.domain.image;

import com.arom.with_travel.domain.accompanies.model.Accompany;
import com.arom.with_travel.domain.community.Community;
import com.arom.with_travel.domain.member.Member;
import com.arom.with_travel.global.entity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Image extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull private String imageName;
    @NotNull private String imageUrl;

    @Enumerated(EnumType.STRING)
    private ImageType imageType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "community_id")
    private Community community;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accompanies_id")
    private Accompany accompany;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private Image(String imageName, String imageUrl, Accompany accompany, ImageType imageType) {
        this.imageName = imageName;
        this.imageUrl = imageUrl;
        this.accompany = accompany;
        this.imageType = imageType;
        this.accompany.getImages().add(this);
    }

    private Image(String imageName, String imageUrl, Member member, ImageType imageType){
        this.imageName = imageName;
        this.imageUrl = imageUrl;
        this.member = member;
        this.imageType = imageType;
        this.member.uploadImage(this);
    }

    private Image(String imageName, String imageUrl, Community community, ImageType imageType){
        this.imageName = imageName;
        this.imageUrl = imageUrl;
        this.imageType = imageType;
        this.community = community;
        this.community.getImages().add(this);
    }

    public static Image fromAccompany(String imageName, String imageUrl, Accompany accompany, ImageType imageType) {
        return new Image(imageName, imageUrl, accompany, imageType);
    }

    public static Image fromMember(String imageName, String imageUrl, Member member, ImageType imageType) {
        return new Image(imageName, imageUrl, member, imageType);
    }

    public static Image fromCommunity(String imageName, String imageUrl, Community community) {
        return new Image(imageName, imageUrl, community, ImageType.COMMUNITY);
    }
}

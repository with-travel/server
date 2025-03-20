package com.arom.with_travel.domain.member;

import com.arom.with_travel.domain.accompanies.model.Accompany;
import com.arom.with_travel.domain.accompanies.model.AccompanyApply;
import com.arom.with_travel.domain.accompanyReviews.AccompanyReviews;
import com.arom.with_travel.domain.chat.Chat;
import com.arom.with_travel.domain.chat.ChatPart;
import com.arom.with_travel.domain.community.Community;
import com.arom.with_travel.domain.community_reply.CommunityReply;
import com.arom.with_travel.domain.image.Image;
import com.arom.with_travel.domain.likes.Likes;
import com.arom.with_travel.domain.notification.Notification;
import com.arom.with_travel.domain.shorts.Shorts;
import com.arom.with_travel.domain.shorts_reply.ShortsReply;
import com.arom.with_travel.domain.survey.Survey;
import com.arom.with_travel.global.entity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE member SET is_deleted = true, deleted_at = now() where id = ?")
@SQLRestriction("is_deleted is FALSE")
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull private String oauthId;
    @NotNull private String email;
    @NotNull private LocalDate birth;
    @NotNull @Enumerated(EnumType.STRING) private Gender gender;
    @NotNull private String phone;
    @NotNull @Enumerated(EnumType.STRING) private LoginType loginType;
    @NotNull private String memberName;
    @NotNull private String nickname;
    @NotNull @Lob private String introduction;
    @NotNull @Enumerated(EnumType.STRING) private TravelType travelType;
    @Enumerated(EnumType.STRING)private Role role;

    public enum Role {
        USER,
        GUEST
    }

    public enum TravelType {
        USER,
        ADMIN
    }

    public enum Gender {
        MALE,
        FEMALE
    }

    public enum LoginType {
        KAKAO
    }

    public Member(String memberName, String email, Role role) {
        super();
    }

    public static Member create(String memberName, String email, Role role) {
        return new Member(memberName, email, role);
    }

    @OneToMany(mappedBy = "member")
    private List<Shorts> shorts = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<ShortsReply> shortsReply = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Accompany> accompanies = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<AccompanyApply> accompanyApplies = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<AccompanyReviews> accompanyReviews = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Community> communities = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<CommunityReply> communityReplies = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Likes> likes = new ArrayList<>();

    @OneToMany(mappedBy = "sender")
    private List<Chat> chats = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<ChatPart> chatParts = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Survey> surveys = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Notification> notifications = new ArrayList<>();

    @OneToOne(mappedBy = "member")
    private Image image;

    @Builder
    public Member(Long id, String oauthId, String email, LocalDate birth, Gender gender,
                  String phone, LoginType loginType, String nickname, String introduction,
                  TravelType travelType, Role role) {
        this.id = id;
        this.oauthId = oauthId;
        this.email = email;
        this.birth = birth;
        this.gender = gender;
        this.phone = phone;
        this.loginType = loginType;
        this.nickname = nickname;
        this.introduction = introduction;
        this.travelType = travelType;
        this.role = role;
    }
}

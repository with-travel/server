package com.arom.with_travel.domain.member;

import com.arom.with_travel.domain.accompanies.model.Accompany;
import com.arom.with_travel.domain.accompanies.model.AccompanyApply;
import com.arom.with_travel.domain.chat.model.Chat;
import com.arom.with_travel.domain.chat.model.ChatPart;
import com.arom.with_travel.domain.community.Community;
import com.arom.with_travel.domain.community_reply.CommunityReply;
import com.arom.with_travel.domain.community_reply.CommunityReplyLike;
import com.arom.with_travel.domain.image.Image;
import com.arom.with_travel.domain.likes.Likes;
import com.arom.with_travel.domain.member.dto.request.MemberSignupRequestDto;
import com.arom.with_travel.domain.shorts.Shorts;
import com.arom.with_travel.domain.shorts_reply.ShortsReply;
import com.arom.with_travel.domain.survey.Survey;
import com.arom.with_travel.global.entity.BaseEntity;
import com.arom.with_travel.global.exception.BaseException;
import com.arom.with_travel.global.exception.error.ErrorCode;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE member SET is_deleted = true, deleted_at = now() where id = ?")
@SQLRestriction("is_deleted is FALSE")
@Builder
@AllArgsConstructor
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String oauthId;

    private String email;
    @Column(length = 255) private String password;

    private LocalDate birth;
    @Enumerated(EnumType.STRING) private Gender gender;
    private String phone;
    @Enumerated(EnumType.STRING) private LoginType loginType;
    private String name;

    @Column(unique = true)
    private String nickname;
    private String introduction;
    @Enumerated(EnumType.STRING) private TravelType travelType;
    @Enumerated(EnumType.STRING) private Role role;
    private Boolean additionalDataChecked = false;

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
        KAKAO,
        LOCAL
    }

    public Member(String memberName, String email, Role role) {
        super();
    }

    private Member(String name, String email, String oauthId){
        this.name    = name;
        this.email   = email;
        this.oauthId = oauthId;
        this.role = Role.GUEST;
    }

    public static Member create(String memberName, String email, Role role) {
        return new Member(memberName, email, role);
    }

    public static Member create(String memberName, String email, String oauthId) {
        return new Member(memberName, email, oauthId);
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
    private List<Community> communities = new ArrayList<>();

    @OneToMany(mappedBy = "member", orphanRemoval = true)
    private List<CommunityReplyLike> replyLikes = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<CommunityReply> communityReplies = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Likes> likes = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Chat> chats = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<ChatPart> chatParts = new ArrayList<>();

    // 회원 생성 직후 아직 설문이 없을 수 있어서 optional = true로 처리
    @OneToOne(mappedBy = "member", optional = true)
    private Survey survey;

    @OneToOne(mappedBy = "member")
    private Image image;

    public void validateNotAlreadyAppliedTo(Accompany accompany) {
        boolean alreadyApplied = accompanyApplies.stream()
                .anyMatch(apply -> apply.getAccompany().equals(accompany));
        if (alreadyApplied) {
            throw BaseException.from(ErrorCode.ACCOMPANY_ALREADY_APPLIED);
        }
    }

    // 신규 회원 최초 가입 처리
    public static Member signUp(String email, String oauthId) {
        return Member.builder()
                .email(email)
                .oauthId(oauthId)
                .loginType(LoginType.KAKAO)
                .role(Role.USER)        // 최초 가입 시 USER
                .build();
    }

    // 신규 회원 추가 정보 등록;
    public void updateExtraInfo(String nickname, LocalDate birth, Gender gender, String introduction,
                                String email, String password, String name, String phone) {
        this.nickname = nickname;
        this.birth    = birth;
        this.gender   = gender;
        this.introduction = introduction;
        this.email    = email;
        this.password = password;
        this.name = name;
        this.phone = phone;
    }

    public void markAdditionalDataChecked() {
        this.additionalDataChecked = true;
    }

    public void uploadImage(Image image){
        this.image = image;
    }

    public void setSurvey(Survey survey) {
        this.survey = survey;
    }

    public void addCommunity(Community community) {
        if (community == null) return;
        community.changeMember(this);
    }

    public void addReply(CommunityReply reply) {
        this.communityReplies.add(reply);
        if (reply.getMember() != this) {
            reply.setMember(this);
        }
    }

    public void addReplyLike(CommunityReplyLike like) {
        this.replyLikes.add(like);
        if (like.getMember() != this) {
            like.setMember(this);
        }
    }
}
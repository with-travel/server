package com.arom.with_travel.domain.member;

import com.arom.with_travel.domain.accompanies.Accompanies;
import com.arom.with_travel.domain.member.enums.Role;
import com.arom.with_travel.domain.shorts.Shorts;
import com.arom.with_travel.global.entity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
    @NotNull private String nickname;
    @NotNull @Lob private String introduction;
    @NotNull @Enumerated(EnumType.STRING) private TravelType travelType;
    private boolean deleted = Boolean.FALSE;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    public enum TravelType {
        USER,
        ADMIN
    }

    public enum Gender {
        MALE,
        FEMALE
    }

    public enum LoginType {
        NAVER,
        KAKAO
    }

    public static Member create(String nickname, String email, Role role) {
        return new Member(nickname, email, role);
    }

    @OneToMany(mappedBy = "member")
    private List<Shorts> shorts = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Accompanies> accompanies = new ArrayList<>();
}

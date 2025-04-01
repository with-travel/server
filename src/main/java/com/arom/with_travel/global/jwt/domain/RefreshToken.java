package com.arom.with_travel.global.jwt.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "refreshToken_id", updatable = false)
    private Long refreshTokenId;

    @Column(name = "member_id", nullable = false, updatable = false, unique = true)
    private Long memberId;

    @Column(name = "refresh_token", nullable = false)
    private String refreshToken;

    private RefreshToken(Long memberId, String refreshToken) {
        this.memberId = memberId;
        this.refreshToken = refreshToken;
    }

    public static RefreshToken create(Long memberId, String refreshToken) {
        return new RefreshToken(memberId, refreshToken);
    }

    public RefreshToken update(String refreshToken) {
        this.refreshToken = refreshToken;
        return this;
    }
}

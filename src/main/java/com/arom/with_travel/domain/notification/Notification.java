package com.arom.with_travel.domain.notification;

import com.arom.with_travel.domain.member.Member;
import com.arom.with_travel.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.aspectj.weaver.ast.Not;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Notification{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long senderId;
    private Long receiverId;
    private String message;
    private String targetUrl;
    private Boolean isRead = false;
    private LocalDateTime occuredAt;

    @Builder
    private Notification(Long senderId, Long receiverId, String message, String targetUrl, LocalDateTime occuredAt) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.message = message;
        this.targetUrl = targetUrl;
        this.occuredAt = occuredAt;
    }

    public static Notification from(Long senderId, Long receiverId, String message, String targetUrl, LocalDateTime occuredAt) {
        return Notification.builder()
                .message(message)
                .receiverId(receiverId)
                .senderId(senderId)
                .targetUrl(targetUrl)
                .occuredAt(occuredAt)
                .build();
    }
}

package com.arom.with_travel.domain.notification.dto;

import com.arom.with_travel.domain.notification.Notification;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class NotificationResponse {
    private String message;
    private Boolean isRead;
    private String targetUrl;

    public static NotificationResponse from(Notification notification) {
        return NotificationResponse.builder()
                .message(notification.getMessage())
                .isRead(notification.getIsRead())
                .build();
    }
}

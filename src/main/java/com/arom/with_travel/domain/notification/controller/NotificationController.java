package com.arom.with_travel.domain.notification.controller;

import com.arom.with_travel.domain.notification.dto.NotificationResponse;
import com.arom.with_travel.domain.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Slice;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/notification")
public class NotificationController {

    private final NotificationService notificationService;

    // 사용자별 알림 구독 엔드포인트
    // 예: GET /notifications/subscribe/1 => 사용자 ID가 1인 사용자가 구독
    @GetMapping("/subscribe/{userId}")
    public SseEmitter subscribe(@PathVariable Long userId) {
        return notificationService.subscribe(userId);
    }

    @GetMapping("/slice")
    public Slice<NotificationResponse> getNotificationsSlice(
            @RequestParam Long receiverId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return notificationService.getNotifications(receiverId, page, size);
    }
}

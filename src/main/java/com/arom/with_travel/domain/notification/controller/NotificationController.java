package com.arom.with_travel.domain.notification.controller;

import com.arom.with_travel.domain.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/notification")
public class NotificationController {

    private final NotificationService notificationService;


}

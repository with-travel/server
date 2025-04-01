package com.arom.with_travel.domain.notification.eventListner;

import com.arom.with_travel.domain.accompanies.dto.event.AccompanyAppliedEvent;
import com.arom.with_travel.domain.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationEventListener {

    private final NotificationService notificationService;

    @EventListener
    public void handleAccompanyAppliedEvent(AccompanyAppliedEvent event) {
        notificationService.sendNotification(event);
    }
}

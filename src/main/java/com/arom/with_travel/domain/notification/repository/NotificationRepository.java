package com.arom.with_travel.domain.notification.repository;

import com.arom.with_travel.domain.notification.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
}

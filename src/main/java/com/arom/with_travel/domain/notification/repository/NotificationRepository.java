package com.arom.with_travel.domain.notification.repository;

import com.arom.with_travel.domain.notification.Notification;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    @Query("SELECT n FROM Notification n WHERE n.receiverId = :receiverId")
    Slice<Notification> findByReceiverIdOrderByOccurredAtDesc(@Param("receiverId") Long receiverId, Pageable pageable);
}

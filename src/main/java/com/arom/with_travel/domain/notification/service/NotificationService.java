package com.arom.with_travel.domain.notification.service;

import com.arom.with_travel.domain.accompanies.dto.event.AccompanyAppliedEvent;
import com.arom.with_travel.domain.notification.Notification;
import com.arom.with_travel.domain.notification.dto.NotificationResponse;
import com.arom.with_travel.domain.notification.properties.NotificationProperties;
import com.arom.with_travel.domain.notification.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
@RequiredArgsConstructor
public class NotificationService {
    // 사용자 ID별로 여러 SseEmitter를 저장 (멀티 디바이스 대응)
    private final Map<Long, List<SseEmitter>> emitters = new ConcurrentHashMap<>();
    private final NotificationRepository notificationRepository;

    // 사용자별 SSE 구독 등록
    public SseEmitter subscribe(Long userId) {
        // 타임아웃은 30분 설정 (필요에 따라 조정)
        SseEmitter emitter = new SseEmitter(30 * 60 * 1000L);
        emitters.computeIfAbsent(userId, key -> new CopyOnWriteArrayList<>()).add(emitter);

        // 연결 종료 또는 오류 발생 시 emitter 제거
        emitter.onCompletion(() -> removeEmitter(userId, emitter));
        emitter.onTimeout(() -> removeEmitter(userId, emitter));
        emitter.onError((e) -> removeEmitter(userId, emitter));

        return emitter;
    }

    // 알림 전송 메서드
    public void sendNotification(AccompanyAppliedEvent event) {
        List<SseEmitter> userEmitters = emitters.get(event.getOwnerId());
        String notificationMessage = event.getProposerNickName() + NotificationProperties.ACCOMPANY_APPLY_MESSAGE;
        String targetUrl = NotificationProperties.ACCOMPANY_NOTICE_TARGET_URL_PREFIX + event.getAccompanyId();
        if (userEmitters != null) {
            // 등록된 모든 emitter에 알림 전송
            for (SseEmitter emitter : userEmitters) {
                try {
                    emitter.send(SseEmitter.event().name("notification").data(notificationMessage));
                } catch (IOException e) {
                    removeEmitter(event.getOwnerId(), emitter);
                }
            }
        }
        Notification notification = Notification.from(
                event.getProposerId(),
                event.getOwnerId(),
                notificationMessage,
                targetUrl,
                event.getOccurredAt()
        );
        notificationRepository.save(notification);
    }

    public Slice<NotificationResponse> getNotifications(Long receiverId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("occurredAt").descending());
        Slice<Notification> notifications = notificationRepository.findByReceiverIdOrderByOccurredAtDesc(receiverId, pageable);
        return notifications.map(NotificationResponse::from);
    }

    // emitter 제거
    private void removeEmitter(Long userId, SseEmitter emitter) {
        List<SseEmitter> userEmitters = emitters.get(userId);
        if (userEmitters != null) {
            userEmitters.remove(emitter);
        }
    }
}

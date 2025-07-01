package com.arom.with_travel.domain.chat.model;

import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@Setter
@Document(collection = "chat_messages")
public class ChatMessageDocument {

    @Id
    private String messageId;

    private String roomId;
    private String userId;
    private String message;
    private LocalDateTime createdAt;

    // 기본 생성자 (스프링 데이터가 내부적으로 사용)
    public ChatMessageDocument() {}

    // 한 줄로 데이터를 담을 편의 생성자
    public ChatMessageDocument(String roomId, String userId, String message, LocalDateTime createdAt) {
        this.roomId = roomId;
        this.userId = userId;
        this.message = message;
        this.createdAt = createdAt;
    }

}
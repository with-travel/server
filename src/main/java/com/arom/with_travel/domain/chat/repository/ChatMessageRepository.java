package com.arom.with_travel.domain.chat.repository;

import com.arom.with_travel.domain.chat.model.ChatMessageDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ChatMessageRepository extends MongoRepository<ChatMessageDocument, String> {

    List<ChatMessageDocument> findByRoomIdOrderByCreatedAtAsc(String roomId);
}
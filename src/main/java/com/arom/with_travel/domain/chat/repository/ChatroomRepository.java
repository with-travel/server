package com.arom.with_travel.domain.chat.repository;

import com.arom.with_travel.domain.chat.Chatroom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChatroomRepository extends JpaRepository<Chatroom,Long> {
    Optional<Chatroom> findChatroomById(Long id);
}

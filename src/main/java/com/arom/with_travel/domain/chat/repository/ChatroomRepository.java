package com.arom.with_travel.domain.chat.repository;

import com.arom.with_travel.domain.chat.model.Chatroom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatroomRepository extends JpaRepository<Chatroom,Long> {
    Chatroom findChatroomByRoomId(String roomId);
}

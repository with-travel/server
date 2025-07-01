package com.arom.with_travel.domain.chat.repository;

import com.arom.with_travel.domain.chat.model.Chat;
import com.arom.with_travel.domain.chat.model.Chatroom;
import com.arom.with_travel.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatRepository extends JpaRepository<Chat,Long> {
    List<Chat> findChatsByChatroomAndMember (Chatroom chatroom, Member member);
}

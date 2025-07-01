package com.arom.with_travel.domain.chat.repository;

import com.arom.with_travel.domain.chat.model.ChatPart;
import com.arom.with_travel.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatPartRepository extends JpaRepository<ChatPart,Long> {
    List<ChatPart> getChatPartsByMember(Member member);
}


package com.arom.with_travel.domain.chat.repository;

import com.arom.with_travel.domain.chat.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRepository extends JpaRepository<Chat,Long> {
}

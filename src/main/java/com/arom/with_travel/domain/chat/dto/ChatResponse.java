package com.arom.with_travel.domain.chat.dto;


import com.arom.with_travel.domain.chat.model.Chat;

public class ChatResponse {
    public record MessageDto(
            String roomId,
            String sender,
            String message,
            Chat.Type type
    ){}

}

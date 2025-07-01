package com.arom.with_travel.domain.chat.dto;

import com.arom.with_travel.domain.chat.model.Chat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChatRequest {
    private static final Logger log = LoggerFactory.getLogger(ChatRequest.class);

    public record MessageDto(
            String roomId,
            String sender,
            String message,
            Chat.Type type
    ){
        public MessageDto withMessage(String newMessage) {
            log.info(newMessage);
            return new MessageDto(this.roomId, this.sender, newMessage, this.type);
        }
    }

}
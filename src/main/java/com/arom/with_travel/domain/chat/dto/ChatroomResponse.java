package com.arom.with_travel.domain.chat.dto;

import java.util.UUID;

public class ChatroomResponse {
    public record ChatroomDto(
            String roomId,
            String roomName
    ){
        public static ChatroomDto add(String roomName){
            return new ChatroomDto(UUID.randomUUID().toString(),roomName);
        }
    }
}

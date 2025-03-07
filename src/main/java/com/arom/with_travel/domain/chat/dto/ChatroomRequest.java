package com.arom.with_travel.domain.chat.dto;

public class ChatroomRequest {

    public record ChatroomDto(
            String roomName,
            String roomId
    ){}

    public record EnterroomDto(
            String roomId,
            String memberMail
    ){}
}
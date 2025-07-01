package com.arom.with_travel.domain.chat.service;

import com.arom.with_travel.domain.chat.dto.ChatRequest;
import com.arom.with_travel.domain.chat.dto.ChatResponse;
import com.arom.with_travel.domain.chat.dto.ChatroomRequest;
import com.arom.with_travel.domain.chat.dto.ChatroomResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ChatroomService {
    void sendMessage(ChatRequest.MessageDto messageDto);

    List<ChatroomResponse.ChatroomDto> findAllRoom();

    ChatroomResponse.ChatroomDto createRoom(ChatroomRequest.ChatroomDto chatroomDto);

    ChatroomResponse.ChatroomDto roomInfo(ChatroomRequest.ChatroomDto chatroomDto);



    //test
    ChatroomResponse.ChatroomDto saveChatroom(String roomname);

    List<ChatroomResponse.ChatroomDto> findRoomByMember(String member);

    List<ChatResponse.MessageDto> getMessageList(ChatroomRequest.EnterroomDto enterroomDto);


    //삭제 예정
    ChatroomResponse.ChatroomDto createRoom(String name);

    ChatroomResponse.ChatroomDto roomInfo(String roomId);
}
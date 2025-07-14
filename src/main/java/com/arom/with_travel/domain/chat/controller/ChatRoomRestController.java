package com.arom.with_travel.domain.chat.controller;


import com.arom.with_travel.domain.chat.dto.ChatResponse;
import com.arom.with_travel.domain.chat.dto.ChatroomRequest;
import com.arom.with_travel.domain.chat.dto.ChatroomResponse;
import com.arom.with_travel.domain.chat.service.ChatService;
import com.arom.with_travel.domain.chat.service.ChatroomServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class ChatRoomRestController {

    private final ChatroomServiceImpl chatService;

    //test 방가져오기
    @GetMapping("/room/findChatroomByMember/{membername}")
    public List<ChatroomResponse.ChatroomDto> findChatroomByMember(@PathVariable String membername){
        return chatService.findRoomByMember(membername);
    }

    //채팅방 입장시 그 전에 채팅들 가져오기
    @GetMapping("/room/getMessage")
    public List<ChatResponse.MessageDto> getMessageList(ChatroomRequest.EnterroomDto enterroomDto){
        return chatService.getMessageList(enterroomDto);
    }

}

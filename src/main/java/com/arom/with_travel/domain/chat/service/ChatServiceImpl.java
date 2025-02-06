package com.arom.with_travel.domain.chat.service;

import com.arom.with_travel.domain.accompanies.Accompanies;
import com.arom.with_travel.domain.chat.Chat;
import com.arom.with_travel.domain.chat.ChatPart;
import com.arom.with_travel.domain.chat.Chatroom;
import com.arom.with_travel.domain.chat.repository.ChatPartRepository;
import com.arom.with_travel.domain.chat.repository.ChatRepository;
import com.arom.with_travel.domain.chat.repository.ChatroomRepository;
import com.arom.with_travel.domain.member.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService{

    @Autowired
    private final ChatPartRepository chatPartRepository;
    private final ChatRepository chatRepository;
    private final ChatroomRepository chatroomRepository;

    //채팅 참여 목록 반환
    public List<Chatroom> getChatListByMember(Member member){
//        Member member = null;
        List<Chatroom> chatrooms = new ArrayList<>();

        List<ChatPart> chatParts = chatPartRepository.findChatPartByMember(member);
        if(chatParts.isEmpty() || chatParts==null){
            //예외처리
        }

        for(ChatPart chatPart : chatParts) chatrooms.add(chatPart.getChatroom());

        return chatrooms;
    }

    //채팅방 생성
    public void addChatroom(Accompanies accompanies,String roomName,Member member){

        Chatroom chatroom =   Chatroom.builder()
                .roomName(roomName)
                .build();

        chatroomRepository.save(chatroom);
        chatPartRepository.save(
                ChatPart.builder()
                        .member(member)
                        .chatroom(chatroom)
                        .build()
        );



    }
}

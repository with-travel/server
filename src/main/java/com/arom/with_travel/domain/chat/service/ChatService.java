package com.arom.with_travel.domain.chat.service;


import com.arom.with_travel.domain.chat.dto.ChatRequest;
import com.arom.with_travel.domain.chat.model.Chat;
import com.arom.with_travel.domain.chat.model.ChatMessageDocument;
import com.arom.with_travel.domain.chat.model.Chatroom;
import com.arom.with_travel.domain.chat.repository.ChatMessageRepository;
import com.arom.with_travel.domain.chat.repository.ChatroomRedisRepository;
import com.arom.with_travel.domain.chat.repository.ChatroomRepository;
import com.arom.with_travel.domain.member.Member;
import com.arom.with_travel.domain.redis.RedisPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatService {
    private final ChatMessageRepository chatRepository;
    private final ChatroomRedisRepository chatroomRedisRepository;
    private final ChatroomRepository chatroomRepository;
    private final RedisPublisher redisPublisher;

    //message
    public void sendMessage(ChatRequest.MessageDto messageDto){
        log.info("Received message: {}", messageDto);
        if (Chat.Type.ENTER.equals(messageDto.type())) {
            chatroomRedisRepository.enterChatRoom(messageDto.roomId());
            String msg = messageDto.sender() + "님이 입장하셨습니다.";
            messageDto = messageDto.withMessage(msg);
            log.info("User {} entered room {}", messageDto.sender(), messageDto.roomId());
        }
        else{
            ChatMessageDocument doc = new ChatMessageDocument(
                    messageDto.roomId(),
                    messageDto.sender(),
                    messageDto.message(),
                    LocalDateTime.now()
            );
            chatRepository.save(doc);
        }


        System.out.println("messageDto: "+messageDto.message());
        redisPublisher.publish(chatroomRedisRepository.getTopic(messageDto.roomId()), messageDto);
    }
}

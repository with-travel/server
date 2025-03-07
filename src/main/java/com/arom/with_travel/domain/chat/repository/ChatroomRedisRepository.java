package com.arom.with_travel.domain.chat.repository;


import com.arom.with_travel.domain.chat.dto.ChatroomResponse;
import com.arom.with_travel.domain.redis.RedisSubscriber;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Repository
@Slf4j
public class ChatroomRedisRepository {
    //채팅방 발행되는 메세지 처리 listener
    private final RedisMessageListenerContainer redisMessageListenerContainer;
    //구독 처리 서비스
    private final RedisSubscriber redisSubscriber;

    private static final String CHAT_ROOMS = "CHAT_ROOM";
    private final RedisTemplate<String, Object> redisTemplate;
    private HashOperations<String, String, ChatroomResponse.ChatroomDto> opsHashChatRoom;

    private Map<String, ChannelTopic> topics;

    @PostConstruct //의존성 주입이 이루어진 후 초기화를 수행하는 메서드 service수행 전 실행됨
    private void init() {
        opsHashChatRoom = redisTemplate.opsForHash();
        topics = new HashMap<>();
        log.info("ChatRoomRepository initialized.");
    }

    public List<ChatroomResponse.ChatroomDto> findAllRoom() {
        log.info("Fetching all chat rooms");
        return opsHashChatRoom.values(CHAT_ROOMS);
    }

    //수정본 *
    public List<ChatroomResponse.ChatroomDto> findRoomByMember(String nickname){
        return opsHashChatRoom.values(nickname);
    }

    public ChatroomResponse.ChatroomDto findRoomById(String id) {
        log.info("Fetching chat room with id={}", id);
        return opsHashChatRoom.get(CHAT_ROOMS, id);
    }

    //채팅방 생성
    public ChatroomResponse.ChatroomDto createChatRoom(String name) {
        ChatroomResponse.ChatroomDto chatroom = ChatroomResponse.ChatroomDto.add(name);
        opsHashChatRoom.put(CHAT_ROOMS, chatroom.roomId(), chatroom);
        log.info("Created chat room with id={} and name={}", chatroom.roomId(), chatroom.roomName());
        return chatroom;
    }

    //수정본 *
    public ChatroomResponse.ChatroomDto createChatRoom(String name, String nickname) {
        ChatroomResponse.ChatroomDto chatroom = ChatroomResponse.ChatroomDto.add(name);
        opsHashChatRoom.put(name, nickname, chatroom);
        log.info("Created chat room with id={} and name={} and nickname={}", chatroom.roomId(), chatroom.roomName(),nickname);
        return chatroom;
    }

    //채팅방 입장
    public void enterChatRoom(String roomId) {
        ChannelTopic topic = topics.get(roomId);
        if (topic == null) {
            topic = new ChannelTopic(roomId);
            redisMessageListenerContainer.addMessageListener(redisSubscriber, topic);
            topics.put(roomId, topic);

            log.info("Created and stored new topic for roomId={}", roomId);

        } else {
            log.info("Topic already exists for roomid={}", roomId);
        }
    }

    public ChannelTopic getTopic(String roomId) {
        ChannelTopic topic = topics.get(roomId);
        if (topic == null) {
            log.warn("Topic not found for roomId={}", roomId);
        }
        return topic;
    }
}

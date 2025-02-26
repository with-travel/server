package com.arom.with_travel.domain.chat;

import com.arom.with_travel.domain.member.Member;
import com.arom.with_travel.global.entity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Chat extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Type type;
    private String message;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member sender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chatroom_id")
    private Chatroom chatroom;

    public enum Type{
        ENTER,TALK
    }

    @Builder
    public Chat(String message, Type type, Member sender, Chatroom chatroom){
        this.message = message;
        this.type = type;
        this.sender = sender;
        this.chatroom = chatroom;
    }
}

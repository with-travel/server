package com.arom.with_travel.domain.chat.model;

import com.arom.with_travel.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Chatroom extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Boolean status = Boolean.FALSE;

    @org.antlr.v4.runtime.misc.NotNull
    private String roomId;
    @NotNull
    private String roomName;


    @OneToMany(mappedBy = "chatroom")
    private List<ChatPart> chatParts = new ArrayList<>();

    @Builder
    public Chatroom(String roomId, String roomName) {
        this.roomId = roomId;
        this.roomName = roomName;
    }

    public void setChatparts(ChatPart chatPart){
        if(this.chatParts.contains(chatPart)) return;
        chatParts.add(chatPart);
    }
}

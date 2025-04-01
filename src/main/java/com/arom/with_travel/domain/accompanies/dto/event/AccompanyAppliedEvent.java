package com.arom.with_travel.domain.accompanies.dto.event;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class AccompanyAppliedEvent {

    private final Long accompanyId;
    private final Long ownerId;
    private final Long proposerId;
    private final String proposerNickName;
    private final LocalDateTime occurredAt = LocalDateTime.now();

    public AccompanyAppliedEvent(Long accompanyId, Long ownerId, Long proposerId,  String proposerNickName) {
        this.accompanyId = accompanyId;
        this.ownerId = ownerId;
        this.proposerId = proposerId;
        this.proposerNickName = proposerNickName;
    }
}

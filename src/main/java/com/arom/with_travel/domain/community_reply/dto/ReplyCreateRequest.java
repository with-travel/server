package com.arom.with_travel.domain.community_reply.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReplyCreateRequest {
    @NotBlank private String content;
}

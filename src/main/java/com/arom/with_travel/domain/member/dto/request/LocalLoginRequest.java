package com.arom.with_travel.domain.member.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LocalLoginRequest {
    @NotBlank @Email
    private String email;

    @NotBlank @Size(min=8, max=64)
    private String password;
}

package com.arom.with_travel.oauth.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String name;
    private String provider; // 네이버 or 카카오
    private String providerId; // OAuth에서 제공하는 ID
}

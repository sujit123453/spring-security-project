package com.bluthinkInc.spring_security_project.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class TokenBlacklisted {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(length = 500)
    private String token;
    private LocalDateTime expiration;

    public TokenBlacklisted(String token, LocalDateTime expiration) {
        this.token = token;
        this.expiration = expiration;
    }

}

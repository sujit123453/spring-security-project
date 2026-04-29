package com.bluthinkInc.spring_security_project.dto.customResponse;


import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserResponseEntity<T> {
    private String message;
    private int status;
    private T data;
    private LocalDateTime timestamp;

    public UserResponseEntity() {
    }

    public UserResponseEntity(String message, int status, T data) {
        this.message = message;
        this.status = status;
        this.data = data;
        this.timestamp = LocalDateTime.now();
    }

}

package com.bluthinkInc.spring_security_project.dto;

import lombok.Data;

@Data
public class LoginResponse {
    private String accessToken;
    private String refreshToken;
    private int user_id;
    private String user_name;
    private String user_role;

    public LoginResponse(String accessToken, String refreshToken, int user_id, String user_name, String user_role) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.user_id = user_id;
        this.user_name = user_name;
        this.user_role = user_role;
    }
}

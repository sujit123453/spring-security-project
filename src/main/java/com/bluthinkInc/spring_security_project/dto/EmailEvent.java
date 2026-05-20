package com.bluthinkInc.spring_security_project.dto;

public class EmailEvent {
    private String email;
    private String username;

    public EmailEvent() {
    }

    public EmailEvent(String email, String username) {
        this.email = email;
        this.username = username;

    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

}

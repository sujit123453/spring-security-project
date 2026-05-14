package com.bluthinkInc.spring_security_project.service;

public interface RefreshTokenService {
    void save(String username,String token);
    void logout(String username);
}

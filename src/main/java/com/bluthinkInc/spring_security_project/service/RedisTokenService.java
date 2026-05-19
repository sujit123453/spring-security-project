package com.bluthinkInc.spring_security_project.service;


public interface RedisTokenService {
    void saveRefreshToken(String username,String refreshToken);
    String getRefreshToken(String username);
    void deleteRefreshToken(String username);
    void blacklistToken(String token, long expirationTime);
    boolean isBlacklisted(String token);
}

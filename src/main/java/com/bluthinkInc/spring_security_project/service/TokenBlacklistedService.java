package com.bluthinkInc.spring_security_project.service;

import java.time.LocalDateTime;

public interface TokenBlacklistedService {
    void blacklistToken(String token, LocalDateTime expiration);
    boolean isBlacklisted(String token);
}

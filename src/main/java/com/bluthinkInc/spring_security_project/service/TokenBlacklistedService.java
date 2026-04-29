package com.bluthinkInc.spring_security_project.service;

import com.bluthinkInc.spring_security_project.model.TokenBlacklisted;
import com.bluthinkInc.spring_security_project.repo.TokenBlacklistedRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TokenBlacklistedService {
    private final TokenBlacklistedRepo tokenBlacklistedRepo;
    public TokenBlacklistedService(TokenBlacklistedRepo tokenBlacklistedRepo){
        this.tokenBlacklistedRepo = tokenBlacklistedRepo;
    }

    public void blacklistToken(String token, LocalDateTime expiration) {
        tokenBlacklistedRepo.save(new TokenBlacklisted(token, expiration));
    }

    public boolean isBlacklisted(String token) {
        return tokenBlacklistedRepo.existsByToken(token);
    }
}

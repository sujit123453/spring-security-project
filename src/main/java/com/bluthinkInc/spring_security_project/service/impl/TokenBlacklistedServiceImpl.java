package com.bluthinkInc.spring_security_project.service.impl;

import com.bluthinkInc.spring_security_project.model.TokenBlacklisted;
import com.bluthinkInc.spring_security_project.repo.TokenBlacklistedRepo;
import com.bluthinkInc.spring_security_project.service.TokenBlacklistedService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TokenBlacklistedServiceImpl implements TokenBlacklistedService {
    private final TokenBlacklistedRepo tokenBlacklistedRepo;
    public TokenBlacklistedServiceImpl(TokenBlacklistedRepo tokenBlacklistedRepo){
        this.tokenBlacklistedRepo = tokenBlacklistedRepo;
    }

    public void blacklistToken(String token, LocalDateTime expiration) {
        tokenBlacklistedRepo.save(new TokenBlacklisted(token, expiration));
    }

    public boolean isBlacklisted(String token) {
        return tokenBlacklistedRepo.existsByToken(token);
    }
}

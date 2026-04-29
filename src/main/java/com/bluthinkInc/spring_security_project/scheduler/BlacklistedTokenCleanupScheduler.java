package com.bluthinkInc.spring_security_project.scheduler;

import com.bluthinkInc.spring_security_project.repo.TokenBlacklistedRepo;
import com.bluthinkInc.spring_security_project.service.TokenBlacklistedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class BlacklistedTokenCleanupScheduler {
    private final TokenBlacklistedRepo tokenBlacklistedRepo;
    public BlacklistedTokenCleanupScheduler(TokenBlacklistedRepo tokenBlacklistedRepo){
        this.tokenBlacklistedRepo = tokenBlacklistedRepo;
    }

    @Scheduled(cron = "0 0 1 * * * ")  // per hour
    public void cleanupExpiredBlacklistedToken() {
        System.out.println("Cleanup up expired Blacklisted Token" + LocalDateTime.now());
        tokenBlacklistedRepo.deleteExpiredBlacklistedToken(LocalDateTime.now());
        System.out.println("Cleaned finished expired Blacklisted token!!");
    }
}

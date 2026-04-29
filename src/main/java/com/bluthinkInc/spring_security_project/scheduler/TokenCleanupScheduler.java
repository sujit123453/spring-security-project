package com.bluthinkInc.spring_security_project.scheduler;

import com.bluthinkInc.spring_security_project.repo.RefreshTokenRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class TokenCleanupScheduler {
    private final RefreshTokenRepo refreshTokenRepo;
    public TokenCleanupScheduler(RefreshTokenRepo refreshTokenRepo){
        this.refreshTokenRepo = refreshTokenRepo;
    }

    @Scheduled(cron = "0 0 1 * * *")  // every per hour
    public void cleanupExpiredRefreshToken() {
        System.out.println("Cleaning up expired refresh tokens" + LocalDateTime.now());

        refreshTokenRepo.deleteExpiredRefreshToken(LocalDateTime.now());
        System.out.println("cleaned up expired refresh tokens");
    }
}

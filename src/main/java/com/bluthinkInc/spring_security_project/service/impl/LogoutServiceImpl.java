package com.bluthinkInc.spring_security_project.service.impl;

import com.bluthinkInc.spring_security_project.service.JWTService;
import com.bluthinkInc.spring_security_project.service.LogoutService;
import com.bluthinkInc.spring_security_project.service.RedisTokenService;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class LogoutServiceImpl implements LogoutService {
    private final JWTService jwtService;
    private final RedisTokenService redisTokenService;
    public LogoutServiceImpl(JWTService jwtService,RedisTokenService redisTokenService){
        this.jwtService = jwtService;
        this.redisTokenService = redisTokenService;
    }
    @Override
    public void logout(String token) {
        Date expiration =
                jwtService.extractExpiration(token);

        long remainingTime =
                expiration.getTime()
                        - System.currentTimeMillis();

        redisTokenService.blacklistToken(
                token,
                remainingTime
        );

        String username =
                jwtService.extractUsername(token);

        redisTokenService.deleteRefreshToken(
                username
        );
    }
}

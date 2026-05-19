package com.bluthinkInc.spring_security_project.service.impl;

import com.bluthinkInc.spring_security_project.service.RedisTokenService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RedisTokenServiceImpl implements RedisTokenService {
     private final RedisTemplate<String,String>redisTemplate;
     public RedisTokenServiceImpl(RedisTemplate<String,String>redisTemplate){
         this.redisTemplate = redisTemplate;
     }

     private static final String REFRESH_PREFIX = "refresh:";
     private static final String BLACKLIST_PREFIX = "blacklist:";


    @Override
    public void saveRefreshToken(String username, String refreshToken) {
        System.out.println("saving refresh token in redis...");
        redisTemplate.opsForValue()
                .set(
                        "refresh:"+username,
                        refreshToken,
                        7,
                        TimeUnit.DAYS

                );
        System.out.println("saved successfully in redis...."
          + username);
    }

    @Override
    public String getRefreshToken(String username) {

        return redisTemplate.opsForValue().get(
                "REFRESH_PREFIX"+username
        );
    }

    @Override
    public void deleteRefreshToken(String username) {
        redisTemplate.delete(
                "REFRESH_PREFIX"+username
        );
    }

    @Override
    public void blacklistToken(String token, long expirationTime) {
        redisTemplate.opsForValue().set(
                "BLACKLIST_PREFIX"+token,
                "blacklisted",
                expirationTime,
                TimeUnit.MILLISECONDS
        );
    }
    @Override
    public boolean isBlacklisted(String token) {
        return Boolean.TRUE.equals(
                redisTemplate.hasKey(
                        BLACKLIST_PREFIX + token
                )
        );
    }
}

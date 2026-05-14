package com.bluthinkInc.spring_security_project.service.impl;

import com.bluthinkInc.spring_security_project.customExceptions.TokenNotFoundException;
import com.bluthinkInc.spring_security_project.model.RefreshToken;
import com.bluthinkInc.spring_security_project.repo.RefreshTokenRepo;
import com.bluthinkInc.spring_security_project.service.RefreshTokenService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {
    private final RefreshTokenRepo refreshTokenRepo;
    public RefreshTokenServiceImpl(RefreshTokenRepo refreshTokenRepo){
        this.refreshTokenRepo = refreshTokenRepo;
    }

    public void save(String username, String token) {
        Optional<RefreshToken> existingToken = refreshTokenRepo.findByUserName(username);
        RefreshToken refreshToken;
        if (existingToken.isPresent()) {
            //override the old refresh token
            refreshToken = existingToken.get();
        } else {
            //creating new refresh token
            refreshToken = new RefreshToken();
            refreshToken.setUserName(username);
        }
        refreshToken.setRefreshToken(token);
        refreshToken.setExpiresIn(LocalDateTime.now().plusDays(7));
        refreshTokenRepo.save(refreshToken);
    }

    public void logout(String username) {
//         refreshTokenRepo.deleteByUserName(username);
        Optional<RefreshToken> token = refreshTokenRepo.findByUserName(username);
        if (token.isEmpty()) {
            throw new TokenNotFoundException("token not found with this username:" + username);
        }
        refreshTokenRepo.deleteByUserName(username);
    }


}

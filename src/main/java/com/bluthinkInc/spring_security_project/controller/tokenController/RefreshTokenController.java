package com.bluthinkInc.spring_security_project.controller.tokenController;

import com.bluthinkInc.spring_security_project.service.JWTService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RefreshTokenController {
    private JWTService jwtService;
    public RefreshTokenController(JWTService jwtService){
        this.jwtService = jwtService;
    }

    @PostMapping("/refresh_token")
    public ResponseEntity<?> getRefreshToken(@RequestHeader("Authorization") String authHeader) {
        String refreshToken = authHeader.substring(7);

        String type = jwtService.extractTokenType(refreshToken);
        String username = jwtService.extractUserName(refreshToken);
        if (!"refresh_token".equals(type)) {
            throw new RuntimeException("Invalid refresh token");
        }
        String role = jwtService.extractRole(refreshToken);
        String newAccessToken = jwtService.generateAccessToken(username, role);
        return ResponseEntity.ok(newAccessToken);
    }

}

package com.bluthinkInc.spring_security_project.controller.authController;

import com.bluthinkInc.spring_security_project.dto.LoginResponse;
import com.bluthinkInc.spring_security_project.dto.customResponse.UserResponseEntity;
import com.bluthinkInc.spring_security_project.model.Users;
import com.bluthinkInc.spring_security_project.service.JWTService;
import com.bluthinkInc.spring_security_project.service.RefreshTokenService;
import com.bluthinkInc.spring_security_project.service.TokenBlacklistedService;
import com.bluthinkInc.spring_security_project.service.UserService;
import io.jsonwebtoken.Jwt;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.ZoneId;

@RestController
@CrossOrigin
public class UserController {
    private final UserService service;
    RefreshTokenService refreshTokenService;
    private final JWTService jwtService;
    private final TokenBlacklistedService tokenBlacklistedService;

    public UserController(UserService service, RefreshTokenService refreshTokenService,
                          JWTService jwtService,TokenBlacklistedService tokenBlacklistedService){
        this.service = service;
        this.refreshTokenService = refreshTokenService;
        this.jwtService = jwtService;
        this.tokenBlacklistedService = tokenBlacklistedService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseEntity<Users>> register(@RequestBody Users user) {
        UserResponseEntity<Users> response = service.register(user);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponseEntity<LoginResponse>> login(@RequestBody Users user) {
        LoginResponse response = service.verify(user);

        return ResponseEntity.ok(
                new UserResponseEntity<>(
                        "Login Successfully",
                        200,
                        response
                )
        );
    }

    @PostMapping("/auth/logout")
    public ResponseEntity<UserResponseEntity<?>> logoutController(Authentication authentication,
                                                                  HttpServletRequest request) {
        String userName = authentication.getName();
        //delete refresh token form db
        refreshTokenService.logout(userName);
        //extract access token
        String token = request.getHeader("Authorization");

        if (token != null && token.startsWith("Bearer ")) {
            String validToken = token.substring(7);

            LocalDateTime expiration =
                    jwtService.extractExpiration(validToken)
                            .toInstant()
                            .atZone(ZoneId.systemDefault())
                            .toLocalDateTime();
            tokenBlacklistedService.blacklistToken(validToken, expiration);
        }
        UserResponseEntity<?> response = new
                UserResponseEntity<>(
                "Logged out successfully",
                200,
                userName
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}

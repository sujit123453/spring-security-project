package com.bluthinkInc.spring_security_project.service;

import com.bluthinkInc.spring_security_project.customExceptions.UserNotFoundException;
import com.bluthinkInc.spring_security_project.dto.LoginResponse;
import com.bluthinkInc.spring_security_project.dto.customResponse.UserResponseEntity;
import com.bluthinkInc.spring_security_project.model.Users;
import com.bluthinkInc.spring_security_project.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepo repo;
    private final RefreshTokenService refreshTokenService;
    private final JWTService jwtService;
    AuthenticationManager authManager;
    public UserService(UserRepo repo,RefreshTokenService refreshTokenService,JWTService jwtService,AuthenticationManager authManager){
        this.repo = repo;
        this.refreshTokenService = refreshTokenService;
        this.jwtService = jwtService;
        this.authManager = authManager;
    }

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public UserResponseEntity<Users> register(Users user) {
        if (user.getRole() == null) {
            user.setRole("ROLE_USER");
        }
        user.setPassword(encoder.encode(user.getPassword()));
        Users savedUser = repo.save(user);

        return new UserResponseEntity<>(
                "registered successfully",
                200,
                savedUser
        );
    }

    public LoginResponse verify(Users user) {
        try {
            Authentication authentication =
                    authManager.authenticate(new UsernamePasswordAuthenticationToken(user.getName(), user.getPassword()));
            if (authentication.isAuthenticated()) {
                Users dbUser = repo.findByName(user.getName());
                String accessToken = jwtService.generateAccessToken(dbUser.getName(), dbUser.getRole());
                String refreshToken = jwtService.generateRefreshToken(user.getName());

                refreshTokenService.save(dbUser.getName(), refreshToken);
                return new LoginResponse(
                        accessToken,
                        refreshToken,
                        dbUser.getId(),
                        dbUser.getName(),
                        dbUser.getRole()
                );
            }
            throw new UserNotFoundException("Your Credentials are incorrect");
        } catch (BadCredentialsException e) {
            throw new UserNotFoundException("Incorrect User and Password");
        }
    }

}

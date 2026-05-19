package com.bluthinkInc.spring_security_project.service;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

public interface JWTService {
    String generateAccessToken(String username,String role);
    String generateRefreshToken(String username);
    SecretKey keyword();
    String extractUserName(String token);
    <T> T extractClaim(String token, Function<Claims,T>claimsResolver);
    Claims extractAllClaims(String token);
    boolean validateToken(String token , UserDetails userDetails);
    String extractRole(String token);
    boolean isTokenExpired(String token);
    Date extractExpiration(String token);
    String extractTokenType(String token);
    String extractUsername(String token);
}

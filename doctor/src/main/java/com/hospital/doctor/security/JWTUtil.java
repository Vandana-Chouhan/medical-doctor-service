package com.hospital.doctor.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JWTUtil {

    @Value("${jwt.secret}")
    private String secret;

    // Parse token and return claims
    public Claims validateToken(String token) {
        return Jwts.parserBuilder()
            .setSigningKey(Keys.hmacShaKeyFor(secret.getBytes()))
            .build()
            .parseClaimsJws(token)
            .getBody();
    }

    // Get username from token (subject)
    public String getUsername(String token) {
        return validateToken(token).getSubject();
    }

    // New method to extract userId
    public Long extractUserId(String token) {
        Claims claims = validateToken(token);
        // userId claim is stored as Number in JWT
        Number userId = (Number) claims.get("userId");
        if (userId == null) {
            throw new RuntimeException("userId not present in token");
        }
        return userId.longValue();
    }
}
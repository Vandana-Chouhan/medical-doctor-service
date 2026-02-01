//package com.hospital.doctor.security;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.security.Keys;
//
//@Component
//public class JWTUtil {
//
//    @Value("${jwt.secret}")
//    private String secret;
//
//    // Parse token and return claims
//    public Claims validateToken(String token) {
//        return Jwts.parserBuilder()
//            .setSigningKey(Keys.hmacShaKeyFor(secret.getBytes()))
//            .build()
//            .parseClaimsJws(token)
//            .getBody();
//    }
//
//    // Get username from token (subject)
//    public String getUsername(String token) {
//        return validateToken(token).getSubject();
//    }
//
//    // New method to extract userId
//    public Long extractUserId(String token) {
//        Claims claims = validateToken(token);
//        // userId claim is stored as Number in JWT
//        Number userId = (Number) claims.get("userId");
//        if (userId == null) {
//            throw new RuntimeException("userId not present in token");
//        }
//        return userId.longValue();
//    }
//}  
package com.hospital.doctor.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;

import java.util.Date;

@Component
public class JWTUtil {

    @Value("${jwt.secret}")
    private String secret;

    // Validate token and return claims
    public Claims validateToken(String token) {
        try {
            return Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(secret.getBytes()))
                .build()
                .parseClaimsJws(token)
                .getBody();
        } catch (ExpiredJwtException e) {
            throw new RuntimeException("Token has expired");
        } catch (MalformedJwtException e) {
            throw new RuntimeException("Invalid token format");
        } catch (SignatureException e) {
            throw new RuntimeException("Invalid token signature");
        } catch (Exception e) {
            throw new RuntimeException("Token validation failed: " + e.getMessage());
        }
    }

    // Check if token is valid (not expired and properly formatted)
    public boolean isTokenValid(String token) {
        try {
            Claims claims = validateToken(token);
            return !isTokenExpired(claims);
        } catch (Exception e) {
            return false;
        }
    }

    // Check if token is expired
    private boolean isTokenExpired(Claims claims) {
        return claims.getExpiration().before(new Date());
    }

    // Get username from token
    public String getUsername(String token) {
        return validateToken(token).getSubject();
    }

    // Extract userId from token
    public Long extractUserId(String token) {
        Claims claims = validateToken(token);
        Number userId = (Number) claims.get("userId");
        if (userId == null) {
            throw new RuntimeException("userId not present in token");
        }
        return userId.longValue();
    }
}

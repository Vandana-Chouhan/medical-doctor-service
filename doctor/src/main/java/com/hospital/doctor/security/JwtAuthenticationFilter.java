//package com.hospital.doctor.security;
//
//import java.util.List;
//
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import io.jsonwebtoken.Claims;
//import java.io.IOException;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.RequiredArgsConstructor;
//
//@Component
//@RequiredArgsConstructor
//public class JwtAuthenticationFilter extends OncePerRequestFilter {
//
//	private final JWTUtil jwtUtil;
//
//	/**
//	 * ✅ Skip JWT filter for public endpoints
//	 */
//	@Override
//	protected boolean shouldNotFilter(HttpServletRequest request) {
//		String path = request.getServletPath();
//		return path.startsWith("/health");
//	}
//
//	@Override
//	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
//			throws ServletException, IOException {
//
//		String authHeader = request.getHeader("Authorization");
//
//		// If no token, just continue (SecurityConfig will decide access)
//		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
//			filterChain.doFilter(request, response);
//			return;
//		}
//
//		String token = authHeader.substring(7);
//
//		try {
//			Claims claims = jwtUtil.validateToken(token);
//			System.out.println("✅ JWT claims: " + claims);
//	        System.out.println("✅ UserId from JWT: " + claims.get("userId"));
//	        System.out.println("✅ Roles from JWT: " + claims.get("roles"));
//	        
//	        Number userIdNum = (Number) claims.get("userId");
//	        Long userId = userIdNum != null ? userIdNum.longValue() : null;
//
////			Long userId = claims.get("userId", Long.class);
//			List<String> roles = claims.get("roles", List.class);
//
//    		var authorities = roles.stream().map(SimpleGrantedAuthority::new).toList();
////			var authorities = roles.stream()
////				    .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
////				    .toList();
//
//
//			UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userId, null,
//					authorities);
//			authentication.setDetails(userId);
//
//			SecurityContextHolder.getContext().setAuthentication(authentication);
//
//		} catch (Exception e) {
//			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//			return;
//		}
//
//		filterChain.doFilter(request, response);
//	}
//}  
package com.hospital.doctor.security;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import java.io.IOException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getServletPath();
        return path.startsWith("/actuator/health") || path.startsWith("/health");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7);

        try {
            // Validate token and extract claims
            if (!jwtUtil.isTokenValid(token)) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("{\"error\":\"Invalid or expired token\"}");
                return;
            }

            Claims claims = jwtUtil.validateToken(token);
            System.out.println("✅ JWT claims: " + claims);

            // Extract userId safely
            Number userIdNum = (Number) claims.get("userId");
            Long userId = userIdNum != null ? userIdNum.longValue() : null;
            
            if (userId == null) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("{\"error\":\"UserId not found in token\"}");
                return;
            }

            // Extract username
            String username = claims.getSubject();
            
            // Extract roles
            @SuppressWarnings("unchecked")
            List<String> roles = (List<String>) claims.get("roles");
            
            if (roles == null || roles.isEmpty()) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("{\"error\":\"No roles found in token\"}");
                return;
            }

            System.out.println("✅ UserId from JWT: " + userId);
            System.out.println("✅ Username from JWT: " + username);
            System.out.println("✅ Roles from JWT: " + roles);

            // Create authorities
            var authorities = roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .collect(Collectors.toList());

            // Create custom principal object
            UserPrincipal userPrincipal = new UserPrincipal(userId, username, roles);

            UsernamePasswordAuthenticationToken authentication = 
                new UsernamePasswordAuthenticationToken(userPrincipal, null, authorities);

            SecurityContextHolder.getContext().setAuthentication(authentication);

        } catch (Exception e) {
            System.err.println("❌ JWT validation failed: " + e.getMessage());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("{\"error\":\"Token validation failed: " + e.getMessage() + "\"}");
            return;
        }

        filterChain.doFilter(request, response);
    }
}

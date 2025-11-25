package com.myfinbank.myfinbank.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.Map;

@Component
public class JwtUtil {

    private final long ACCESS_EXPIRATION = 1000 * 60 * 30;     // 30 minutes
    private final long REFRESH_EXPIRATION = 1000L * 60 * 60 * 24 * 7; // 7 days

    // ✅ Correct Key generation using JJWT SignatureAlgorithm
    private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    public String generateAccessToken(String username, String role) {
        return buildToken(username, role, "ACCESS", ACCESS_EXPIRATION);
    }

    public String generateRefreshToken(String username, String role) {
        return buildToken(username, role, "REFRESH", REFRESH_EXPIRATION);
    }

    private String buildToken(String username, String role, String type, long expiryMillis) {
        long now = System.currentTimeMillis();

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + expiryMillis))
                .addClaims(Map.of(
                        "role", role,
                        "type", type
                ))
                .signWith(key, SignatureAlgorithm.HS256) // 🔥 Correct signature algorithm
                .compact();
    }

    public String extractUsername(String token) {
        return parseClaims(token).getSubject();
    }

    public String extractRole(String token) {
        return parseClaims(token).get("role", String.class);
    }

    public String extractType(String token) {
        return parseClaims(token).get("type", String.class);
    }

    public boolean isTokenValid(String token) {
        try {
            parseClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    private Claims parseClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}

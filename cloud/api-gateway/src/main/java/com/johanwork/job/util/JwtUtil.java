package com.johanwork.job.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

@Service
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    public static final String TOKEN_PREFIX = "Bearer";
    public static final String JWT_HEADER = "Authorization";

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public Claims extractAllClaims(String token){
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String extractEmail(String token){
        return extractAllClaims(token).getSubject();
    }

    public String extractAuthorities(String token){
        return extractAllClaims(token).get("authorities", String.class);
    }

    public String extractUserId(String token){
        return extractAllClaims(token).get("userId", String.class);
    }

    public boolean isTokenValid(String token){
        try {
            extractAllClaims(token);
            return true;
        }catch (Exception e){
            return false;
        }
    }

}

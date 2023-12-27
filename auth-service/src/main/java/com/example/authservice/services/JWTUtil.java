package com.example.authservice.services;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class JWTUtil {
    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expiration}")
    private String expiration;
    private Key key;

    @PostConstruct
    public void initKey() {
        key = Keys.hmacShaKeyFor(secret.getBytes());
    }

    public String generate(String userId) {
        long expMillis = Long.parseLong(expiration) * 1000;

        Date now = new Date();
        Date exp = new Date(now.getTime() + expMillis);

        return Jwts
                .builder()
                .setSubject(userId)
                .setIssuedAt(now)
                .setExpiration(exp)
                .signWith(key)
                .compact();
    }
}

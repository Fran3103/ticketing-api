package com.fran.ticketing_api.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;
import java.util.Map;

@Service
public class JwtService {

    private final SecretKey key;
    private final long accessExpMinutes;

    public JwtService(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.access.expMinutes}") long accessExpMinutes
    ) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.accessExpMinutes = accessExpMinutes;
    }

    public String generateAccessToken(String subjectEmail, Map<String, Object> extraClaims) {
        Instant now = Instant.now();
        Instant exp = now.plusSeconds(accessExpMinutes * 60);

        return Jwts.builder()
                .setSubject(subjectEmail)
                .addClaims(extraClaims)
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(exp))
                .signWith(key)
                .compact();
    }

    public Jws<Claims> parse(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token);
    }

    public String getSubject(String token) {
        return parse(token).getBody().getSubject();
    }

    public boolean isValid(String token) {
        try {
            parse(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public long accessExpiresInSeconds() {
        return accessExpMinutes * 60;
    }
}

package com.example.auth.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

@Component
public class JwtUtil {

  @Value("${app.jwt.secret}")
  private String secret;

  @Value("${app.jwt.ttlMinutes}")
  private long ttlMinutes;

  public String generateToken(String username, List<String> roles) {
    Instant now = Instant.now();
    Instant exp = now.plus(ttlMinutes, ChronoUnit.MINUTES);

    return Jwts.builder()
        .setSubject(username)
        .claim("roles", roles)
        .setIssuedAt(Date.from(now))
        .setExpiration(Date.from(exp))
        .signWith(Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8)), SignatureAlgorithm.HS256)
        .compact();
  }
}

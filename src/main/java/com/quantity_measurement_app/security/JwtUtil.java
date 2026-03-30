package com.quantity_measurement_app.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;

@Component
public class JwtUtil {

	// it load secret from application.properties for flexibility
	@Value("${jwt.secret}")
	private String secret;

	// here token validity is 24 hours
	private static final long EXPIRATION_TIME = 1000 * 60 * 60 * 24;

	private SecretKey getSigningKey() {
		return Keys.hmacShaKeyFor(secret.getBytes());
	}

	// it generate token with claims of email and role
	public String generateToken(String email, String role) {
		return Jwts.builder().setSubject(email).addClaims(Map.of("role", role)).setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
				.signWith(getSigningKey(), SignatureAlgorithm.HS256).compact();
	}

	public String extractEmail(String token) {
		return getClaims(token).getSubject();
	}

	public String extractRole(String token) {
		return (String) getClaims(token).get("role");
	}

	public boolean validateToken(String token, String email) {
		try {
			return email.equals(extractEmail(token)) && !isTokenExpired(token);
		} catch (JwtException | IllegalArgumentException e) {
			return false;
		}
	}

	private boolean isTokenExpired(String token) {
		return getClaims(token).getExpiration().before(new Date());
	}

	private Claims getClaims(String token) {
		return Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token).getBody();
	}
}
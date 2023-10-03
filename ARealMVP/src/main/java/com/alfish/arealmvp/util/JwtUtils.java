package com.alfish.arealmvp.util;

import com.alfish.arealmvp.service.ConfigProvider;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.function.Function;

@Component
public class JwtUtils {

	// Validity duration in seconds               HH : MM : SS
	public static final long JWT_TOKEN_VALIDITY = 24 * 60 * 60;

	public final ConfigProvider config;

	@Autowired
	public JwtUtils(ConfigProvider config) {
		this.config = config;
	}

	public String getUsernameFromToken(String token) {
		return getClaimFromToken(token, Claims::getSubject);
	}

	public Date getExpirationDateFromToken(String token) {
		return getClaimFromToken(token, Claims::getExpiration);
	}

	public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
		return claimsResolver.apply(getAllClaimsFromToken(token));
	}

	private Claims getAllClaimsFromToken(String token) {
		return Jwts.parser().setSigningKey(config.JWT_SECRET()).parseClaimsJws(token).getBody();
	}

	private Boolean isTokenExpired(String token) {
		return getExpirationDateFromToken(token).before(new Date());
	}

	public String generateToken(String username) {
		return doGenerateToken(username);
	}

	private String doGenerateToken(String subject) {
		return Jwts.builder().setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
				.signWith(SignatureAlgorithm.HS512, config.JWT_SECRET()).compact();
	}

	public Boolean validateToken(String token, String username) {
		return getUsernameFromToken(token).equals(username) && !isTokenExpired(token);
	}

}
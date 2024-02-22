package com.ms.loginapp.loginapp.jwt;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;


@Service
public class JwtService {
	
	private static final String SECRET_KEY="4843534R84383F83T83G484F834F348F348F438F384R8567F6DF6TSD6FTSDFF";

	public String getToken(UserDetails userDetails) {
		
		
	return getToken(new HashMap<>(), userDetails);
	
	}
	private String getToken(Map<String,Object> extraClaims, UserDetails user) {
		
		
		return Jwts.builder().claims(extraClaims).subject(user.getUsername())
				.expiration(new Date(System.currentTimeMillis()+1200*60*24))
				.signWith(getKey())
				.compact();
	}
	
	private SecretKey  getKey() {
		
		byte[] keyByte=Decoders.BASE64.decode(SECRET_KEY);
		return Keys.hmacShaKeyFor(keyByte);
	}
	
	
	public String getUsernameFromToken(String token) {

		return getClaims(token,Claims::getSubject);
	}
	public boolean isTokenValid(String token, UserDetails userDetails) {
		final String username=getUsernameFromToken(token);
		return (username.equals(userDetails.getUsername())&& !istokenExpired(token));

	}
	
	private Claims getAllClaims(String token) {
		
		return Jwts.parser().verifyWith(getKey()).build().parseSignedClaims(token).getPayload();
		
	}
	
	public <T> T getClaims(String token,Function<Claims, T> ClaimsResolver) {
		
		
		final var claims=getAllClaims(token);
		return ClaimsResolver.apply(claims);
		
	}
	
	private Date getExpirationDate(String token) {
		
		return getClaims(token, Claims::getExpiration);
		
	}
	
	private boolean istokenExpired(String token) {
		
		return getExpirationDate(token).before(new Date());
		
	}
}

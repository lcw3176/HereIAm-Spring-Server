package com.joebrooks.hereIam.service;

import java.security.Key;
import java.util.Date;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtService implements IJwtService {

	private static final String SECRET_KEY = "dsfsdfdsfsefdscxvdfewgfbddsfe";

	@Override
	public String createToken(String subject, long mills) {
		
		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

		byte[] secretKeyBytes = DatatypeConverter.parseBase64Binary(SECRET_KEY);
		Key signingKey = new SecretKeySpec(secretKeyBytes, signatureAlgorithm.getJcaName());
		
		String jwt = Jwts.builder()
				.setHeaderParam("typ", "JWT")
				.setHeaderParam("regDate", System.currentTimeMillis())
				.setSubject(subject)
				.setExpiration(new Date(System.currentTimeMillis() + mills))
				.signWith(signatureAlgorithm, signingKey)
				.compact();

		return jwt;
	}

	
	@Override
	public String createMobileToken(String subject) {
		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

		byte[] secretKeyBytes = DatatypeConverter.parseBase64Binary(SECRET_KEY);
		Key signingKey = new SecretKeySpec(secretKeyBytes, signatureAlgorithm.getJcaName());
		
		String jwt = Jwts.builder()
				.setHeaderParam("typ", "JWT")
				.setHeaderParam("regDate", System.currentTimeMillis())
				.setSubject(subject)
				.signWith(signatureAlgorithm, signingKey)
				.compact();

		return jwt;
	}

	

	@Override
	public String getSubject(String token) {
		Claims claims = Jwts.parser()
							.setSigningKey(DatatypeConverter.parseBase64Binary(SECRET_KEY))
							.parseClaimsJws(token)
							.getBody();

		return claims.getSubject();
	}

	@Override
	public boolean isEnable(String token) {
		try {
			Jwts.parser()
				.setSigningKey(DatatypeConverter.parseBase64Binary(SECRET_KEY))
				.parse(token);
			
			return true;
			
		} catch (Exception e) {
			return false;
		}

	}




}

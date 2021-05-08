package com.example.spring_boot_security_demo.filter;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.spring_boot_security_demo.entity.UsernameAndPasswordDto;
import com.example.spring_boot_security_demo.jwt.JwtConfig;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Jwts;

/**
 * <p>
 * The job of this filter is to verify the credentials.
 * </p>
 * 
 * @author mayur.somani
 */
public class JwtUsernameAndPasswordFilter extends UsernamePasswordAuthenticationFilter {

	private static final Logger logger = LoggerFactory.getLogger(JwtUsernameAndPasswordFilter.class);
	
	private final AuthenticationManager authenticationManager;
	private final SecretKey secretKey;
	private final JwtConfig jwtConfig;
	
	public JwtUsernameAndPasswordFilter(AuthenticationManager authenticationManager, SecretKey secretKey,
			JwtConfig jwtConfig) {
		this.authenticationManager = authenticationManager;
		this.secretKey = secretKey;
		this.jwtConfig = jwtConfig;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {

		try {
			UsernameAndPasswordDto usernameAndPasswordDto = new ObjectMapper().readValue(request.getInputStream(),
					UsernameAndPasswordDto.class);
			Authentication authentication = new UsernamePasswordAuthenticationToken(
					usernameAndPasswordDto.getUsername(), usernameAndPasswordDto.getPassword());
			return authenticationManager.authenticate(authentication);

		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		
		String token = Jwts.builder()
			.setSubject(authResult.getName())
			.claim("authorities", authResult.getAuthorities())
			.setIssuedAt(new Date())
			.setExpiration(java.sql.Date.valueOf(LocalDate.now().plusDays(jwtConfig.getTokenExpirationAfterDays())))
			.signWith(secretKey)
			.compact();
		
		response.addHeader(jwtConfig.getAuthorizationHeader(), jwtConfig.getTokenPrefix() + token);
	}

}

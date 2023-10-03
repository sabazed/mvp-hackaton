package com.alfish.arealmvp.security;

import com.alfish.arealmvp.service.ConfigProvider;
import com.alfish.arealmvp.util.JwtUtils;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class AuthorizationFilter extends BasicAuthenticationFilter {

	private final JwtUtils jwtUtils;
	private final ConfigProvider config;

	public AuthorizationFilter(AuthenticationManager authenticationManager, JwtUtils jwtUtils, ConfigProvider config) {
		super(authenticationManager);
		this.jwtUtils = jwtUtils;
		this.config = config;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request,
	                                HttpServletResponse response,
	                                FilterChain chain) throws IOException, ServletException {
		String token = request.getHeader(config.JWT_HEADER());

		if (token == null || !token.startsWith(config.JWT_PREFIX())) {
			chain.doFilter(request, response);
			return;
		}

		UsernamePasswordAuthenticationToken auth = getAuthentication(request, response);
		SecurityContextHolder.getContext().setAuthentication(auth);
		chain.doFilter(request, response);
	}

	private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request, HttpServletResponse response) {
		String token = request.getHeader(config.JWT_HEADER());
		if (token != null) {
			token = token.replace(config.JWT_PREFIX(), "");
			try {
				String username = jwtUtils.getUsernameFromToken(token);
				if (username != null) {
					return new UsernamePasswordAuthenticationToken(username, null, List.of());
				}
			}
			catch (ExpiredJwtException e) {
				response.addHeader(config.JWT_HEADER(), "EXPIRED");
			}
		}
		return null;
	}
}

package com.alfish.arealmvp.security;

import com.alfish.arealmvp.message.UserLoginRequest;
import com.alfish.arealmvp.service.ConfigProvider;
import com.alfish.arealmvp.util.JwtUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private final AuthenticationManager authenticationManager;
	private final JwtUtils jwtUtils;
	private final ConfigProvider config;


	public AuthenticationFilter(AuthenticationManager authenticationManager, JwtUtils jwtUtils, ConfigProvider config) {
		this.authenticationManager = authenticationManager;
		this.jwtUtils = jwtUtils;
		this.config = config;
		this.setFilterProcessesUrl("/user/login");
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res) throws AuthenticationException {
		try {
			UserLoginRequest request = new ObjectMapper().readValue(req.getInputStream(), UserLoginRequest.class);
			return authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
						request.getUsername(),
						request.getPassword(),
						List.of()
				)
			);
		}
		catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
	                                        FilterChain chain, Authentication authResult) throws IOException, ServletException {
		String username = ((User) authResult.getPrincipal()).getUsername();
		String token = jwtUtils.generateToken(username);
		response.addHeader(config.JWT_HEADER(), config.JWT_PREFIX() + token);

		SecurityContextHolder.getContext().setAuthentication(authResult);
		chain.doFilter(request, response);
	}

}

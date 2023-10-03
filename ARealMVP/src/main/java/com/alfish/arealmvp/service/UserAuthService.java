package com.alfish.arealmvp.service;

import com.alfish.arealmvp.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserAuthService implements UserDetailsService {

	private final UserService userService;

	@Autowired
	public UserAuthService(UserService userService) {
		this.userService = userService;
	}

	@Override
	public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
		User user = userService.getUser(login);
		if (user == null) throw new UsernameNotFoundException(login);
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), List.of());
	}

}

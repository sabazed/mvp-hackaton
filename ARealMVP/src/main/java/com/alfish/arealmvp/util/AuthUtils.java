package com.alfish.arealmvp.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.util.regex.Pattern;

@Component
public class AuthUtils {

	private static final Pattern USERNAME = Pattern.compile("^(?=[a-zA-Z0-9._]{8,20}$)(?!.*[_.]{2})[^_.].*[^_.]$");
	private static final Pattern PASSWORD = Pattern.compile("^\\w{8,}$");

	private final BCryptPasswordEncoder encoder;

	@Autowired
	public AuthUtils(BCryptPasswordEncoder encoder) {
		this.encoder = encoder;
	}

	public boolean validateEmail(String email) {
		try {
			InternetAddress emailAddress = new InternetAddress(email);
			emailAddress.validate();
		}
		catch (AddressException e) {
			return false;
		}
		return true;
	}

	public boolean validateUsername(String username) {
		return matchesPattern(username, USERNAME);
	}

	public boolean validatePassword(String password) {
		return matchesPattern(password, PASSWORD);
	}

	private boolean matchesPattern(String text, Pattern pattern) {
		return pattern.matcher(text).matches();
	}

	public String encodePassword(String password) {
		return encoder.encode(password);
	}

	public boolean matchPassword(String password, String encoded) {
		return encoder.matches(password, encoded);
	}

}

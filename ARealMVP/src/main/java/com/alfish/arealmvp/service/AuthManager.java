package com.alfish.arealmvp.service;

import com.alfish.arealmvp.enums.AuthRejectReason;
import com.alfish.arealmvp.message.*;
import com.alfish.arealmvp.model.User;
import com.alfish.arealmvp.util.AuthUtils;
import com.alfish.arealmvp.util.PasswordGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;

@Service
public class AuthManager {

	private final AuthUtils utils;
	private final ConfigProvider config;
	private final PasswordGenerator pswdGenerator;

	@Autowired
	public AuthManager(AuthUtils utils, ConfigProvider config, PasswordGenerator pswdGenerator) {
		this.utils = utils;
		this.config = config;
		this.pswdGenerator = pswdGenerator;
	}

	public UserRegisterResponse process(UserRegisterRequest request, User user) {
		AuthRejectReason reason = null;
		if (user != null) {
			reason = AuthRejectReason.INCORRECT_USERNAME;
		}
		else if (!utils.validateEmail(request.getEmail())) {
			reason = AuthRejectReason.INCORRECT_EMAIL;
		}
		else if (!utils.validateUsername(request.getUsername())) {
			reason = AuthRejectReason.INCORRECT_USERNAME;
		}
		else if (!utils.validatePassword(request.getPassword())) {
			reason = AuthRejectReason.INCORRECT_PASSWORD;
		}
		else if (!request.getPassword().equals(request.getPasswordConfirm())){
			reason = AuthRejectReason.PASSWORDS_MISMATCH;
		}
		return new UserRegisterResponse(request.getEmail(), request.getUsername(), reason);
	}

	public UserLoginResponse process(User user, HttpServletResponse response) {
		return new UserLoginResponse(user.getUsername(), response.getHeader(config.JWT_HEADER()), null);
	}

	public ChangePasswordResponse process(User user, ChangePasswordRequest request) {
		AuthRejectReason reason = null;
		if (user == null) {
			reason = AuthRejectReason.INVALID_USERNAME;
		}
		else if (!utils.matchPassword(request.getOldPassword(), user.getPassword())) {
			reason = AuthRejectReason.INVALID_PASSWORD;
		}
		else if (!utils.validatePassword(request.getNewPassword())) {
			reason = AuthRejectReason.INCORRECT_PASSWORD;
		}
		else if (!request.getNewPassword().equals(request.getNewPasswordConfirm())) {
			reason = AuthRejectReason.PASSWORDS_MISMATCH;
		}
		return new ChangePasswordResponse(request.getUsername(), reason);
	}

	public String generatePassword() {
		return pswdGenerator.generateSecureRandomPassword();
	}

	public boolean isKeyValid(User user, String key) {
		return utils.matchPassword(key, user.getResetKey());
	}

}

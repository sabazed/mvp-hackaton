package com.alfish.arealmvp.controller;

import com.alfish.arealmvp.message.*;
import com.alfish.arealmvp.model.User;
import com.alfish.arealmvp.service.MailService;
import com.alfish.arealmvp.service.AuthManager;
import com.alfish.arealmvp.service.UserService;
import com.alfish.arealmvp.util.PasswordGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final AuthManager authManager;
    private final MailService mailService;

    @Autowired
    public UserController(UserService userService, AuthManager authManager, MailService mailService) {
        this.userService = userService;
        this.authManager = authManager;
        this.mailService = mailService;
    }

    @PostMapping("/login")
    @ResponseBody
    public UserLoginResponse userLogin(@AuthenticationPrincipal org.springframework.security.core.userdetails.User principal,
                                       HttpServletResponse response) {
        User user = userService.getUser(principal.getUsername());
        return authManager.process(user, response);
    }

    @PostMapping("/register")
    @ResponseBody
    public UserRegisterResponse userRegister(@RequestBody UserRegisterRequest request) {
        User user = userService.getUser(request.getEmail());
        UserRegisterResponse response = authManager.process(request, user);
        if (response.getRejectReason() == null) {
            String resetKey = userService.addUser(request);
            mailService.sendRegisterConfirmation(request.getEmail(), resetKey);
        }
        return response;
    }

    @PutMapping("/reset")
    public void userForgotPassword(@RequestParam("user") String login, @RequestParam("token") String key) {
        User user = userService.getUser(login);
        if (user == null) return;
        if (!authManager.isKeyValid(user, key)) return;
        String password = authManager.generatePassword();
        String resetKey = authManager.generatePassword();
        userService.changeUserPassword(user, password, resetKey);
        mailService.sendPasswordReset(user.getEmail(), password, resetKey);
    }

    @PutMapping("/change")
    @ResponseBody
    public ChangePasswordResponse userChangePassword(@RequestBody ChangePasswordRequest request) {
        User user = userService.getUser(request.getUsername());
        ChangePasswordResponse response = authManager.process(user, request);
        if (response.getRejectReason() == null) {
            userService.changeUserPassword(user, request.getNewPassword(), null);
        }
        return response;
    }

}

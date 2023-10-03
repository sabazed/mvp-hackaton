package com.alfish.arealmvp.service;


import com.alfish.arealmvp.message.UserRegisterRequest;
import com.alfish.arealmvp.model.User;
import com.alfish.arealmvp.repository.UserRepository;
import com.alfish.arealmvp.util.AuthUtils;
import com.alfish.arealmvp.util.PasswordGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class UserService {

    private final UserRepository repository;
    private final AuthUtils utils;
    private final PasswordGenerator generator;

    @Autowired
    public UserService(UserRepository repository, AuthUtils utils, PasswordGenerator generator) {
        this.repository = repository;
        this.utils = utils;
        this.generator = generator;
    }

    public User getUser(String login) {
        if (utils.validateEmail(login)) {
            return getUserByEmail(login);
        }
        else if (utils.validateUsername(login)) {
            return getUserByUsername(login);
        }
        else {
            return null;
        }
    }

    public User storeUser(User user) {
        return repository.save(user);
    }

    private User getUserByEmail(String email) {
        return repository.findByEmail(email);
    }

    private User getUserByUsername(String username) {
        return repository.findByUsername(username);
    }

    public String addUser(UserRegisterRequest request) {
        String resetKey = generator.generateSecureRandomPassword();
        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(utils.encodePassword(request.getPassword()));
        user.setResetKey(utils.encodePassword(resetKey));
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setSex(request.getSex());
        user.setBirthday(Instant.ofEpochMilli(request.getBirthday()));
        user.setCreationDate(Instant.now());
        user.setViews(0);
        user.setInteractions(0);
        user.setWrites(0);
        this.storeUser(user);
        return resetKey;
    }

    public void changeUserPassword(User user, String newPassword, String newResetKey) {
        user.setPassword(utils.encodePassword(newPassword));
        if (newResetKey != null) {
            user.setResetKey(utils.encodePassword(newResetKey));
        }
        this.storeUser(user);
    }

    public void addInteraction(User user) {
        if (user != null) {
            user.setInteractions(user.getInteractions() + 1);
            storeUser(user);
        }
    }

    public void addView(User user) {
        if (user != null) {
            user.setViews(user.getViews() + 1);
            storeUser(user);
        }
    }

    public void addWrite(User user) {
        if (user != null) {
            user.setWrites(user.getWrites() + 1);
            storeUser(user);
        }
    }

}


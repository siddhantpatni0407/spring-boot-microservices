package com.sid.app.controller;

import com.sid.app.constants.AppConstants;
import com.sid.app.entity.user.User;
import com.sid.app.repository.user.UserRepository;
import com.sid.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(value = AppConstants.SAVE_USER_ENDPOINT, produces = MediaType.APPLICATION_JSON_VALUE)
    public User saveUser(@RequestBody User user) {
        return userService.saveUser(user);
    }

    @GetMapping(value = AppConstants.GET_USERS_ENDPOINT, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<User> getUserList() {
        return userService.getUserList();
    }

}
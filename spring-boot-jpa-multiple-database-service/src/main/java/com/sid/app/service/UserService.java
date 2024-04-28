package com.sid.app.service;

import com.sid.app.entity.user.User;
import com.sid.app.repository.user.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Siddhant Patni
 */
@Service
@Slf4j
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public List<User> getUserList() {
        return userRepository.findAll();
    }

}
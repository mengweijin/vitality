package com.mwjwork.sample.system.controller;

import com.mwjwork.sample.system.entity.User;
import com.mwjwork.sample.system.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Meng Wei Jin
 * @description
 * @date Create in 2019-07-27 17:27
 **/
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/find")
    public User findUser(){
        User user = new User(1L, "vitas", 20);
        userRepository.save(user);

        user = userRepository.findById(1L).get();

        return user;
    }
}

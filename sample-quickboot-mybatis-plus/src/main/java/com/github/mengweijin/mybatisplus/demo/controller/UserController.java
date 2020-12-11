package com.github.mengweijin.mybatisplus.demo.controller;

import com.github.mengweijin.mybatisplus.demo.entity.User;
import com.github.mengweijin.mybatisplus.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author mengweijin
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/get")
    public List<User> getUser(){
        return userService.list();
    }

    @PostMapping("/saveOrUpdate")
    public void addUser(@RequestBody User user){
        userService.saveOrUpdate(user);
    }
}

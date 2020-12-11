package com.github.mengweijin.mybatisplus.demo.controller;

import com.github.mengweijin.mybatisplus.demo.entity.User;
import com.github.mengweijin.mybatisplus.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

    @GetMapping("/get/{id}")
    public User getUserById(@PathVariable("id") Long id){
        return userService.getById(id);
    }
    @PostMapping("/save")
    public void addUser(@RequestBody User user){
        userService.saveOrUpdate(user);
    }

    @PutMapping("/update")
    public void updateUser(@RequestBody User user){
        userService.saveOrUpdate(user);
    }

    @DeleteMapping("/delete/{id}")
    public void updateUser(@PathVariable("id") Long id){
        userService.removeById(id);
    }
}

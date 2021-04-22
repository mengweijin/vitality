package com.github.mengweijin.mybatisplus.demo.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.mengweijin.mybatisplus.demo.async.AsyncFactory;
import com.github.mengweijin.mybatisplus.demo.entity.User;
import com.github.mengweijin.mybatisplus.demo.service.UserService;
import com.github.mengweijin.quickboot.framework.util.SpringUtils;
import com.github.mengweijin.quickboot.mybatis.page.Pager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @author mengweijin
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AsyncFactory asyncFactory;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @GetMapping("/get")
    public List<User> getUser(){
        return userService.list();
    }

    @GetMapping("/call")
    public String callApi(String api) throws JsonProcessingException {
        log.info("api=" + api);
        Object object = restTemplate.getForObject(api, Object.class);
        log.info("object=" + object);
        String result = objectMapper.writeValueAsString(object);
        log.info("result=" + result);
        return result;
    }

    @GetMapping("/page")
    public IPage<User> getPage(Pager<User> pager){
        return userService.page(pager);
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

    @GetMapping("/i18n")
    public String i18n(){
        return SpringUtils.getMessage("operation.successful");
    }

    @GetMapping("/async")
    public String async(String name) throws ExecutionException, InterruptedException {
        Future<String> stringFuture = asyncFactory.doTaskInAsync(name);
        log.info("Continue execute code.");
        String s = stringFuture.get();
        log.info("Async Completed. result=" + s);
        return "async";
    }
}

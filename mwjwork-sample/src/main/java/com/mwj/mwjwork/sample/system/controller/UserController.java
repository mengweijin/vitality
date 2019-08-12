package com.mwj.mwjwork.sample.system.controller;

import com.mwj.mwjwork.framework.page.Pager;
import com.mwj.mwjwork.framework.web.controller.BaseController;
import com.mwj.mwjwork.sample.system.entity.User;
import com.mwj.mwjwork.sample.system.repository.UserRepository;
import com.mwj.mwjwork.sample.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Meng Wei Jin
 * @description
 * @date Create in 2019-07-27 17:27
 **/
@RestController
@RequestMapping("/user")
public class UserController extends BaseController {

    @Autowired
    private UserRepository userRepository;


    @Autowired
    private UserService userService;


    @PostMapping("/insert")
    public void insert(){
        userService.insert();
    }

    @PostMapping("/update/{id}")
    public void update(@PathVariable("id") Long id){
        User user = new User();
        user.setId(id);
        user.setName("Tom");
        userRepository.saveDynamicAndFlush(id, user);
    }

    @PostMapping("/findAll")
    public List<User> findAll(){
        return userRepository.findAll();
    }

    @PostMapping("/findPage")
    public Page findPage(Pager pager){
        Pageable pageable = PageRequest.of(pager.getPage(), pager.getLimit());
        return userRepository.findAll(pageable);
    }
}

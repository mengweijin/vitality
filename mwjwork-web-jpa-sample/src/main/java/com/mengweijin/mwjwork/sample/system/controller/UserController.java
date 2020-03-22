package com.mengweijin.mwjwork.sample.system.controller;
import com.mengweijin.mwjwork.framework.jpa.page.Pager;
import com.mengweijin.mwjwork.framework.web.BaseController;
import com.mengweijin.mwjwork.sample.system.entity.User;
import com.mengweijin.mwjwork.sample.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * @author Meng Wei Jin
 * @description
 * @date Create in 2019-07-27 17:27
 **/
@Validated
@RestController
@RequestMapping("/user")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;


    @PostMapping("/insert")
    public void insert(@Valid User user){
        userService.saveAndFlush(user);
    }

    @PostMapping("/update/{id}")
    public void update(@PathVariable("id") Long id){
        User user = new User();
        user.setId(id);
        user.setName("Tom");
        userService.update(id, user);
    }

    @PostMapping("/all")
    public List<User> findAll(){
        return userService.findAll();
    }

    @PostMapping("/page")
    public Page findPage(Pager pager){
        Pageable pageable = PageRequest.of(pager.getCurrent(), pager.getSize());
        return userService.findAll(pageable);
    }
}

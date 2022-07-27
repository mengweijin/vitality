package com.github.mengweijin.flowable.service;

import com.github.mengweijin.flowable.entity.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author mengweijin
 * @date 2022/6/21
 */
@Service
public class UserService {

    public static final String ROLE_CEO = "CEO";
    public static final String ROLE_Leader = "Leader";
    public static final String ROLE_Developer = "Developer";

    public static final String DEPT_CEO = "CEO";
    public static final String DEPT_Finance = "Finance";
    public static final String DEPT_Develop = "Develop";

    public List<User> getAllUser(){
        List<User> userList = new ArrayList<>();
        userList.add(new User().setUsername("Tom").setPassword("123456").setRole(ROLE_CEO).setDept(DEPT_CEO));

        userList.add(new User().setUsername("Lucy").setPassword("123456").setRole(ROLE_Leader).setDept(DEPT_Finance));

        userList.add(new User().setUsername("Jerry").setPassword("123456").setRole(ROLE_Leader).setDept(DEPT_Develop));

        userList.add(new User().setUsername("Jack").setPassword("123456").setRole(ROLE_Developer).setDept(DEPT_Develop));
        userList.add(new User().setUsername("James").setPassword("123456").setRole(ROLE_Developer).setDept(DEPT_Develop));

        return userList;
    }

    public User getCEO(){
        return getAllUser().stream().filter(user -> DEPT_CEO.equals(user.getDept())).findFirst().get();
    }

    public User getFinanceLeader(){
        return getAllUser().stream().filter(user -> DEPT_Finance.equals(user.getDept())).findFirst().get();
    }

    public User getDevelopLeader(){
        return getAllUser().stream().filter(user -> DEPT_Develop.equals(user.getDept())).findFirst().get();
    }

    public List<User> getAllLeader(){
        return getAllUser().stream().filter(user -> ROLE_Leader.equals(user.getRole())).collect(Collectors.toList());
    }

    public List<User> getAllDeveloper(){
        return getAllUser().stream().filter(user -> ROLE_Developer.equals(user.getRole())).collect(Collectors.toList());
    }
}

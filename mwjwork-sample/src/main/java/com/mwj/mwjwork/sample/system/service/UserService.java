package com.mwj.mwjwork.sample.system.service;

import com.mwj.mwjwork.sample.system.entity.User;
import com.mwj.mwjwork.sample.system.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Meng Wei Jin
 * @description
 * @date Create in 2019-07-28 0:36
 **/
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void insert(){
        User user;
        for(int i = 1; i <= 100; i++){
            user = new User();
            user.setName("vitas " + i);
            user.setAge(i);
            user = userRepository.saveAndFlush(user);
        }
    }

}

package com.github.mengweijin.quickboot.sample.system.service;

import com.github.mengweijin.quickboot.jpa.service.BaseService;
import com.github.mengweijin.quickboot.sample.system.entity.User;
import com.github.mengweijin.quickboot.sample.system.repository.UserRepository;

import java.util.List;

/**
 * @author Meng Wei Jin
 * @date Create in 2019-07-28 0:36
 **/
public interface UserService extends BaseService<User, Long, UserRepository> {

    List<User> findAllByQueryDsl(User user);

    List<Object> findStudentAddress(String userName, String city);
}

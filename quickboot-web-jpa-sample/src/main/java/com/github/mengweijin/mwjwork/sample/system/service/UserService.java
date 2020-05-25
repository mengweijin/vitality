package com.github.mengweijin.mwjwork.sample.system.service;

import com.github.mengweijin.mwjwork.jpa.service.BaseService;
import com.github.mengweijin.mwjwork.sample.system.entity.User;
import com.github.mengweijin.mwjwork.sample.system.repository.UserRepository;
import java.util.List;

/**
 * @author Meng Wei Jin
 * @description
 * @date Create in 2019-07-28 0:36
 **/
public interface UserService extends BaseService<User, Long, UserRepository> {

    List<User> findAllByQueryDsl(User user);

    List<Object> findStudentAddress(String userName, String city);
}

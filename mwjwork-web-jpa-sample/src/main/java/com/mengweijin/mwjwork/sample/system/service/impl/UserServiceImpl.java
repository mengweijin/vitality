package com.mengweijin.mwjwork.sample.system.service.impl;

import com.mengweijin.mwjwork.jpa.service.BaseServiceImpl;
import com.mengweijin.mwjwork.sample.system.entity.User;
import com.mengweijin.mwjwork.sample.system.repository.UserRepository;
import com.mengweijin.mwjwork.sample.system.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Meng Wei Jin
 * @date Create in 2019-10-29 21:46
 **/
@Transactional(rollbackFor = Exception.class)
@Service
public class UserServiceImpl extends BaseServiceImpl<User, Long, UserRepository> implements UserService {

}

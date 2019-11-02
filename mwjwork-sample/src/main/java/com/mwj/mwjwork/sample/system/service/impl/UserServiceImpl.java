package com.mwj.mwjwork.sample.system.service.impl;

import com.mwj.mwjwork.framework.web.service.BaseServiceImpl;
import com.mwj.mwjwork.sample.system.entity.User;
import com.mwj.mwjwork.sample.system.repository.UserRepository;
import com.mwj.mwjwork.sample.system.service.UserService;
import org.springframework.stereotype.Service;

/**
 * @author Meng Wei Jin
 * @date Create in 2019-10-29 21:46
 **/
@Service
public class UserServiceImpl extends BaseServiceImpl<User, Long, UserRepository> implements UserService {

}

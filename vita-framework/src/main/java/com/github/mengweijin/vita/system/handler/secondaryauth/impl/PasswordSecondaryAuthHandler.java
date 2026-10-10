package com.github.mengweijin.vita.system.handler.secondaryauth.impl;

import cn.hutool.v7.extra.spring.SpringUtil;
import com.github.mengweijin.vita.framework.enums.dict.ESafeMode;
import com.github.mengweijin.vita.framework.exception.ServerException;
import com.github.mengweijin.vita.system.domain.bo.OpenSafeBO;
import com.github.mengweijin.vita.system.domain.entity.UserDO;
import com.github.mengweijin.vita.system.domain.vo.user.UserSessionVO;
import com.github.mengweijin.vita.system.handler.secondaryauth.ISecondaryAuthHandler;
import com.github.mengweijin.vita.system.service.UserService;
import org.springframework.stereotype.Component;

/**
 *
 * @author mengweijin
 * @since 2026/3/14
 */
@Component
public class PasswordSecondaryAuthHandler implements ISecondaryAuthHandler {

    @Override
    public ESafeMode supported() {
        return ESafeMode.PASSWORD;
    }

    @Override
    public boolean validate(UserSessionVO sessionUser, OpenSafeBO bo) {
        if(sessionUser == null) {
            throw new ServerException("二级认证失败，用户未登录");
        }
        UserService userService = SpringUtil.getBean(UserService.class);
        UserDO user = userService.getById(sessionUser.getUserId());
        return userService.checkPassword(bo.getValue(), user.getPassword());
    }
}

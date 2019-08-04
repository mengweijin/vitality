package com.mwj.mwjwork.framework.web.controller;

import com.mwj.mwjwork.common.constant.Const;
import com.mwj.mwjwork.framework.util.ServletUtils;
import com.mwj.mwjwork.framework.web.entity.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.List;

/**
 * @author Meng Wei Jin
 * @description
 **/
@Transactional(rollbackFor = {Exception.class})
@Slf4j
public class BaseController {

    /**
     * 页面重定向
     */
    public String redirect(String url) {
        return String.format("redirect:%s", url);
    }

    /**
     * 设置请求参数
     * @param key
     * @param value
     */
    public void setAttribute(String key, Object value){
        ServletUtils.getRequest().setAttribute(key, value);
    }

    /**
     * 数据校验发现不合法结果处理
     * @param bindingResult
     * @return
     */
    public Result validateErrorResult(BindingResult bindingResult){
        StringBuilder sb = new StringBuilder();
        List<ObjectError> errors = bindingResult.getAllErrors();
        for (ObjectError err : errors) {
            sb.append(err.getDefaultMessage() + Const.SEMICOLON + Const.NEWLINE + Const.NEWLINE_HTML);
        }

        return Result.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), sb.toString());
    }
}

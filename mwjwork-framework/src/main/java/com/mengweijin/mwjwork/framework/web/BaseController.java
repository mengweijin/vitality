package com.mengweijin.mwjwork.framework.web;

import com.mengweijin.mwjwork.framework.constant.Const;
import com.mengweijin.mwjwork.framework.util.ServletUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;

import java.util.List;

/**
 * @author Meng Wei Jin
 * @description
 **/
@Slf4j
@Validated
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
    public ErrorInfo validateErrorResult(BindingResult bindingResult){
        List<ObjectError> errors = bindingResult.getAllErrors();

        ErrorInfo errorInfo = new ErrorInfo().setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        for (ObjectError err : errors) {
            errorInfo.addMessage(err.getDefaultMessage() + Const.SEMICOLON);
        }

        return errorInfo;
    }

}

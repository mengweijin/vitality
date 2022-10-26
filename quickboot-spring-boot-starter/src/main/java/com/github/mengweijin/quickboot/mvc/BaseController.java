package com.github.mengweijin.quickboot.mvc;

import com.github.mengweijin.quickboot.domain.R;
import com.github.mengweijin.quickboot.util.ServletUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.view.UrlBasedViewResolver;

import java.util.List;

/**
 * @author Meng Wei Jin
 **/
@Slf4j
@Validated
public abstract class BaseController {

    public abstract String prefix();

    @GetMapping("/index")
    public String index() {
        return prefix() + "/index";
    }

    /**
     * 页面重定向: UrlBasedViewResolver.REDIRECT_URL_PREFIX
     * 页面转发: UrlBasedViewResolver.FORWARD_URL_PREFIX
     * 建议使用Restful风格，前后端分离
     */
    public String redirect(String url) {
        return UrlBasedViewResolver.REDIRECT_URL_PREFIX + url;
    }

    /**
     * 设置请求参数
     * 建议使用Restful风格提供接口获取数据，前后端分离，而不是在后台通过在request中获取数据
     */
    public void setAttribute(String key, Object value){
        ServletUtils.getRequest().setAttribute(key, value);
    }

    /**
     * 数据校验发现不合法结果处理
     * 建议使用全局异常捕获处理
     *
     * @param bindingResult bindingResult
     */
    @Deprecated
    public R validateErrorResult(BindingResult bindingResult) {
        List<ObjectError> errors = bindingResult.getAllErrors();

        R r = R.error(HttpStatus.BAD_REQUEST.value(), null);
        for (ObjectError err : errors) {
            r.appendMessage(err.getDefaultMessage());
        }

        return r;
    }

}

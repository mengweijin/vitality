package com.mengweijin.mwjwork.framework.web;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Meng Wei Jin
 * @description 实体类属性名称对应layui表格请求和响应所需要的默认参数名称
 **/
@Data
@Accessors(chain = true)
public class ErrorInfo {

    private static final long serialVersionUID = 1967183638333866256L;

    /**
     * 异常的状态码，默认 500
     */
    private int code = HttpStatus.INTERNAL_SERVER_ERROR.value();

    /**
     * Error message list
     */
    private List<String> messageList = new ArrayList<>();

    /**
     * add an error message
     * @param message message
     * @return this
     */
    public ErrorInfo addMessage(String message){
        this.messageList.add(message);
        return this;
    }

}

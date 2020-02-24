package com.mengweijin.mwjwork.framework.web.entity;

import com.mengweijin.mwjwork.common.constant.Const;
import com.mengweijin.mwjwork.framework.util.MessageSourceUtils;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;

import java.util.Collections;
import java.util.List;

/**
 * @author Meng Wei Jin
 * @description 实体类属性名称对应layui表格请求和响应所需要的默认参数名称
 **/
@Data
@Accessors(chain = true)
@NoArgsConstructor
@RequiredArgsConstructor
public class AjaxResponse<T> {

    private static final long serialVersionUID = 1967183638333866256L;

    /**
     * 成功的状态码，默认 0 为成功
     */
    private int code = 0;

    /**
     * 附加描述信息
     */
    private List<String> messageList = Collections.emptyList();

    /**
     * 返回数据
     */
    @NonNull
    private T data;

    public AjaxResponse<?> addMessage(String message){
        messageList.add(message);
        return this;
    }

    /**
     * 返回成功消息
     *
     * @param msg 内容
     * @return 成功消息
     */
    public AjaxResponse<?> success(String msg) {
        return this.addMessage(msg);
    }

    public AjaxResponse<?> success() {
        return this.success(MessageSourceUtils.message("operation.successful"));
    }

    /**
     * 返回失败消息
     *
     * @param code 错误码
     * @param msg  内容
     * @return
     */
    public AjaxResponse<?> error(int code, String msg) {
        return this.setCode(code).addMessage(msg);
    }

    /**
     * 默认错误类型为500
     *
     * @param msg
     * @return
     */
    public AjaxResponse<?> error(String msg) {
        return this.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), msg);
    }

    public AjaxResponse<?> error() {
        return this.error(MessageSourceUtils.message("operation.failed"));
    }

    /**
     * @param rows 受影响的行数
     * @return
     */
    public AjaxResponse<?> resultByRows(int rows) {
        return rows > 0 ? success() : error();
    }

    /**
     * @param flag
     * @return
     */
    public AjaxResponse<?> resultByBoolean(Boolean flag) {
        return flag ? success() : error();
    }

    public AjaxResponse<?> resultByBoolean(Boolean flag, String msg) {
        msg = StringUtils.isBlank(msg) ? Const.EMPTY : msg;
        return flag ? success(msg) : error(msg);
    }
}

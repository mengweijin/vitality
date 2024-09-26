package com.github.mengweijin.vitality.monitor.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.github.mengweijin.vitality.framework.mybatis.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author mengweijin
 * @since 2023-06-03
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("VTL_LOG_OPERATION")
public class LogOperation extends BaseEntity {

    /**
    * 请求url
    */
    private String url;

    /**
    * 请求参数
    */
    private String requestArgs;

    /**
    * 请求体 request body
    */
    private String requestBody;

    /**
    * http 请求方式
    */
    private String httpMethod;

    /**
    * 请求方法名称
    */
    private String methodName;

    /**
    * 操作是否成功。[Y, N]
    */
    private String success;

    /**
    * 失败信息
    */
    private String errorMsg;
}

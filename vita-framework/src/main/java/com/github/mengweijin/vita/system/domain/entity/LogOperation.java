package com.github.mengweijin.vita.system.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.github.mengweijin.vita.framework.log.aspect.enums.EOperationType;
import com.github.mengweijin.vita.framework.mybatis.entity.BaseEntity;
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
@TableName("VT_LOG_OPERATION")
public class LogOperation extends BaseEntity {

    /**
     * 操作日志模块标题
     */
    private String title;

    /**
     * 操作类型枚举：{@link EOperationType}
     */
    private String operationType;

    /**
     * http 请求方式
     */
    private String httpMethod;

    /**
     * 请求url
     */
    private String url;

    /**
     * 请求方法名称
     */
    private String methodName;

    /**
     * 请求数据
     */
    private String requestData;

    /**
     * 响应数据
     */
    private String responseData;

    /**
     * 执行消耗时间（毫秒）
     */
    private Long costTime;

    /**
    * 操作是否成功。[Y, N]
    */
    private String success;

    /**
    * 失败信息
    */
    private String errorMsg;
}

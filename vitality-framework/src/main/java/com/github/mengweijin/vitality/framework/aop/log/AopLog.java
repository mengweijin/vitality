package com.github.mengweijin.vitality.framework.aop.log;

import com.github.mengweijin.vitality.framework.mybatis.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author mengweiijin
 */
@Deprecated
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class AopLog extends BaseEntity {

    /** request url */
    private String url;

    /** Http request method */
    private String httpMethod;

    /** request methodName */
    private String methodName;

    /** operate IP */
    private String ip;

    /** Operate status */
    private Boolean success;

    /** error information */
    private String error;

}

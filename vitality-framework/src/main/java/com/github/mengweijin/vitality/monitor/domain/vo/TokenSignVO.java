package com.github.mengweijin.vitality.monitor.domain.vo;

import com.github.mengweijin.vitality.framework.jackson.sensitive.ESensitiveStrategy;
import com.github.mengweijin.vitality.framework.jackson.sensitive.Sensitive;
import lombok.Data;

/**
 * @author mengweijin
 * @since 2024/11/10
 */
@Data
public class TokenSignVO {

    /**
     * Token 值
     */
    @Sensitive(strategy = ESensitiveStrategy.TOKEN)
    private String value;

    /**
     * 所属设备类型
     */
    private String device;

    /**
     * 此客户端登录的挂载数据
     */
    private Object tag;
}

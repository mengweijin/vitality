package com.github.mengweijin.vitality.monitor.domain.vo;

import cn.dev33.satoken.session.TokenSign;
import com.github.mengweijin.vitality.framework.jackson.sensitive.ESensitiveStrategy;
import com.github.mengweijin.vitality.framework.jackson.sensitive.Sensitive;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author mengweijin
 * @since 2024/11/10
 */
@Data
@NoArgsConstructor
public class TokenSignVO {

    /**
     * Token 值
     */
    @Sensitive(strategy = ESensitiveStrategy.TOKEN)
    private String token;

    /**
     * Token 加密值
     */
    @Sensitive(strategy = ESensitiveStrategy.ENCRYPT)
    private String value;

    /**
     * 所属设备类型
     */
    private String device;

    /**
     * 此客户端登录的挂载数据
     */
    private Object tag;

    public TokenSignVO(TokenSign tokenSign) {
        this.token = tokenSign.getValue();
        this.value = tokenSign.getValue();
        this.device = tokenSign.getDevice();
        this.tag = tokenSign.getTag();
    }
}

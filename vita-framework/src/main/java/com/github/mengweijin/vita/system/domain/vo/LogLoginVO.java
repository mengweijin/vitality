package com.github.mengweijin.vita.system.domain.vo;

import com.github.mengweijin.vita.framework.jackson.sensitive.ESensitiveStrategy;
import com.github.mengweijin.vita.framework.jackson.sensitive.Sensitive;
import com.github.mengweijin.vita.system.domain.entity.LogLogin;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author mengweijin
 * @since 2024/11/10
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class LogLoginVO extends LogLogin {

    @Sensitive(strategy = ESensitiveStrategy.IP)
    private String ip;
}

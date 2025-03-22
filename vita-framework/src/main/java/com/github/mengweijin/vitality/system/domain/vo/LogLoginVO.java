package com.github.mengweijin.vitality.system.domain.vo;

import com.github.mengweijin.vitality.framework.jackson.sensitive.ESensitiveStrategy;
import com.github.mengweijin.vitality.framework.jackson.sensitive.Sensitive;
import com.github.mengweijin.vitality.system.domain.entity.LogLogin;
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

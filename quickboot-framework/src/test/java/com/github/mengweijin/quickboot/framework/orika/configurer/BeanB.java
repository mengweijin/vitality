package com.github.mengweijin.quickboot.framework.orika.configurer;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @author mengweijin
 * @date 2022/4/3
 */
@Data
@Accessors(chain = true)
public class BeanB {

    private String nameB;

    private String mail;

    private LocalDateTime createTime;
}

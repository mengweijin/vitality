package com.github.mengweijin.quickboot.framework.orika.configurer;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author mengweijin
 * @date 2022/4/3
 */
@Data
@Accessors(chain = true)
public class BeanA {

    private String nameA;

    private String mail;

    private Date createTime;
}

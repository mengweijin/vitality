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

    private String nameA = "Tom";

    private String mail = "a@a.com";

    private String stringToDate = "2022-01-01 00:00:00";

    private String stringToLocalDateTime = "2022-01-01 00:00:00";

    private Date date = new Date();
}

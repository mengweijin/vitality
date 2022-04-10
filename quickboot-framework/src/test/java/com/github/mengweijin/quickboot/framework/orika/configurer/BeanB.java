package com.github.mengweijin.quickboot.framework.orika.configurer;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author mengweijin
 * @date 2022/4/3
 */
@Data
@Accessors(chain = true)
public class BeanB {

    private String nameB;

    private String mail;

    private Date stringToDate;

    private LocalDateTime stringToLocalDateTime;

    private LocalDate localDate;
    private LocalDateTime localDateTime;

}

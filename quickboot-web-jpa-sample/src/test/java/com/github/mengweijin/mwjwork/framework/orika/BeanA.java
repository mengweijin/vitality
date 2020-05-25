package com.github.mengweijin.mwjwork.framework.orika;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BeanA {

    private String nameA;

    private Integer ageA;

    private String mail;

    private String localDate;

    private String localTime;

    private String localDateTime;

    private String normDate;

    private String normDateTimeMinute;

    private String normDateTimeMs;

    private String normDateTime;

    private String chineseDate;

}

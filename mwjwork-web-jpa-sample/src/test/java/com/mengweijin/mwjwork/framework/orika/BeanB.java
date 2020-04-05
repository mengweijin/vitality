package com.mengweijin.mwjwork.framework.orika;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BeanB {

    private String nameB;

    private Integer ageB;

    private String mail;

    private LocalDate localDate;

    private LocalTime localTime;

    private LocalDateTime localDateTime;

    private Date normDate;

    private Date normDateTimeMinute;

    private Date normDateTimeMs;

    private Date normDateTime;

    private Date chineseDate;

}

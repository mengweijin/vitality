package com.github.mengweijin.quickboot.sample.system.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author mengweijin
 * @date 2022/4/3
 */
@Data
@EqualsAndHashCode
public class AddressDto {

    private String country;

    private String province;

    private String city;

    private Integer houseNumber;
}

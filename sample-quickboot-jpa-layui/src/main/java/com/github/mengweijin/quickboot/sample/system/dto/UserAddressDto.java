package com.github.mengweijin.quickboot.sample.system.dto;

import com.github.mengweijin.quickboot.sample.system.enums.Role;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author mengweijin
 */
@Data
@EqualsAndHashCode
public class UserAddressDto {

    private String name;

    private Integer age;

    private Role role;

    private AddressDto address;

}

package com.mengweijin.mwjwork.sample.system.dto;

import com.mengweijin.mwjwork.jpa.BaseEntity;
import com.mengweijin.mwjwork.sample.system.entity.Address;
import com.mengweijin.mwjwork.sample.system.enums.Role;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author mengweijin
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UserAddressDTO extends BaseEntity {

    private String name;

    private Integer age;

    private Role role;

    private Long addressId;

    private Address address;

}

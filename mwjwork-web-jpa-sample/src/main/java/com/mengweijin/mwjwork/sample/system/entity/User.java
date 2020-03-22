package com.mengweijin.mwjwork.sample.system.entity;

import com.mengweijin.mwjwork.jpa.BaseEntity;
import com.mengweijin.mwjwork.sample.system.enums.Role;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

/**
 * @author Meng Wei Jin
 * @description
 * @date Create in 2019-07-27 12:49
 **/

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class User extends BaseEntity {

    @NotBlank
    @Column(length = 30)
    private String name;

    @Min(value = 1)
    @Max(value = 150)
    @Column(length = 3)
    private Integer age;

    @Enumerated(value = EnumType.STRING)
    @Column
    private Role role;
}

package com.github.mengweijin.quickboot.sample.system.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.github.mengweijin.quickboot.jpa.BaseEntity;
import com.github.mengweijin.quickboot.sample.system.enums.Role;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

/**
 * @author Meng Wei Jin
 * @date Create in 2019-07-27 12:49
 **/
@Data
@Entity
@Accessors(chain = true)
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

    @JsonSerialize(using = ToStringSerializer.class)
    @Column
    private Long addressId;

}

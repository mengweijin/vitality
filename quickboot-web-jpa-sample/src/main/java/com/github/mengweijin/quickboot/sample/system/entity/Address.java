package com.github.mengweijin.quickboot.sample.system.entity;

import com.github.mengweijin.quickboot.jpa.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * @author mengweijin
 */
@Data
@Entity(name = "address")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Address extends BaseEntity {

    @Column
    private String country;

    @Column
    private String province;

    @Column
    private String city;

    @Column
    private Integer houseNumber;

}

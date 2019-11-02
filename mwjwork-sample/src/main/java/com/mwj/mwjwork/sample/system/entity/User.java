package com.mwj.mwjwork.sample.system.entity;

import com.mwj.mwjwork.framework.validator.CharsetLength;
import com.mwj.mwjwork.framework.web.entity.BaseEntity;
import com.mwj.mwjwork.sample.system.enums.UserType;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;

import javax.persistence.*;

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

    @CharsetLength(max = 10)
    @Column(length = 30)
    private String name;

    @Column(length = 3)
    private Integer age;

    @Enumerated(value = EnumType.STRING)
    @Column
    private UserType userType;
}

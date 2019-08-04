package com.mwj.mwjwork.sample.system.entity;

import com.mwj.mwjwork.framework.web.entity.BaseEntity;
import lombok.Data;

import javax.persistence.*;

/**
 * @author Meng Wei Jin
 * @description
 * @date Create in 2019-07-27 12:49
 **/

@Entity
@Data
public class User extends BaseEntity {

    @Column(length = 30)
    private String name;

    @Column(length = 3)
    private Integer age;


}

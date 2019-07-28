package com.mwj.mwjwork.sample.system.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mwj.mwjwork.common.util.date.DateFormatUtil;
import com.mwj.mwjwork.framework.entity.BaseEntity;
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

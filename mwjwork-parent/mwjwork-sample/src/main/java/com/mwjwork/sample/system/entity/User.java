package com.mwjwork.sample.system.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @author Meng Wei Jin
 * @description
 * @date Create in 2019-07-27 12:49
 **/

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue
    @Column(length = 32)
    private Long id;

    @Column(length = 30)
    private String name;

    @Column(length = 3)
    private Integer age;
}

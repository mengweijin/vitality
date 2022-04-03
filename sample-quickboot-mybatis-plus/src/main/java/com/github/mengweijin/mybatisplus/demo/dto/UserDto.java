package com.github.mengweijin.mybatisplus.demo.dto;

import lombok.Data;

/**
 * @author mengweijin
 * @date 2022/4/3
 */
@Data
public class UserDto {
    private String name;

    private String gender;

    private Integer deleted;
}

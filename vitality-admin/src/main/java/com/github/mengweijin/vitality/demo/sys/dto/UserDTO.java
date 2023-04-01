package com.github.mengweijin.vitality.demo.sys.dto;

import com.github.mengweijin.vitality.demo.sys.entity.Order;
import com.github.mengweijin.vitality.demo.sys.entity.User;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author mengweijin
 * @date 2022/11/26
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UserDTO extends User {

    private List<Order> orderList;

}

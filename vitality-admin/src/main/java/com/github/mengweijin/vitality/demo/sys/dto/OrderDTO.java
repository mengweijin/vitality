package com.github.mengweijin.vitality.demo.sys.dto;

import com.github.mengweijin.vitality.demo.sys.entity.Order;
import com.github.mengweijin.vitality.demo.sys.entity.User;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author mengweijin
 * @date 2022/11/26
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class OrderDTO extends Order {

    private User owner;

}

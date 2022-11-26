package com.github.mengweijin.vitality.demo.sys.controller;

import com.github.mengweijin.vitality.demo.sys.entity.Order;
import com.github.mengweijin.vitality.demo.sys.mapper.OrderMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author mengweijin
 * @date 2022/11/26
 */
@Slf4j
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderMapper orderMapper;

    @GetMapping("/list")
    public List<Order> list(){
        return orderMapper.findList();
    }
}

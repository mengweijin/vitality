package com.github.mengweijin.vitality.demo.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.mengweijin.vitality.demo.sys.entity.Order;
import com.github.mengweijin.vitality.demo.sys.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author mengweijin
 */
@Mapper
public interface OrderMapper extends BaseMapper<User> {

    List<Order> findList();

}

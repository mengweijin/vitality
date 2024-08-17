package com.github.mengweijin.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.mengweijin.system.domain.entity.Menu;
import org.apache.ibatis.annotations.Mapper;

import java.util.Set;

/**
 * <p>
 *  Menu Mapper
 * </p>
 *
 * @author mengweijin
 * @since 2023-06-03
 */
@Mapper
public interface MenuMapper extends BaseMapper<Menu> {

    Set<String> selectPermissionListByUsername(String username);
}


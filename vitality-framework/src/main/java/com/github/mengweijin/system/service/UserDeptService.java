package com.github.mengweijin.system.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.mengweijin.system.domain.entity.UserDept;
import com.github.mengweijin.system.mapper.UserDeptMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.Objects;

/**
 * <p>
 *  UserDept Service
 *  Add @Transactional(rollbackFor = Exception.class) if you need.
 * </p>
 *
 * @author mengweijin
 * @since 2023-06-03
 */
@Slf4j
@Service
public class UserDeptService extends ServiceImpl<UserDeptMapper, UserDept> {

    /**
     * Custom paging query
     * @param page page
     * @param userDept {@link UserDept}
     * @return IPage
     */
    public IPage<UserDept> page(IPage<UserDept> page, UserDept userDept){
        LambdaQueryWrapper<UserDept> query = new LambdaQueryWrapper<>();
        query
                .eq(!Objects.isNull(userDept.getUserId()), UserDept::getUserId, userDept.getUserId())
                .eq(!Objects.isNull(userDept.getDeptId()), UserDept::getDeptId, userDept.getDeptId())
                .eq(!Objects.isNull(userDept.getId()), UserDept::getId, userDept.getId())
                .eq(!Objects.isNull(userDept.getCreateBy()), UserDept::getCreateBy, userDept.getCreateBy())
                .eq(!Objects.isNull(userDept.getCreateTime()), UserDept::getCreateTime, userDept.getCreateTime())
                .eq(!Objects.isNull(userDept.getUpdateBy()), UserDept::getUpdateBy, userDept.getUpdateBy())
                .eq(!Objects.isNull(userDept.getUpdateTime()), UserDept::getUpdateTime, userDept.getUpdateTime());
        return this.page(page, query);
    }
}

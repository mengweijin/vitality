package com.github.mengweijin.quickboot.auth.system.mapper;

import com.github.mengweijin.quickboot.auth.system.entity.Auth;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;

/**
 * <p>
 * 权限表 Mapper Interface
 * </p>
 *
 * @author mengweijin
 * @since 2022-01-07
 */
@Mapper
public interface AuthMapper extends BaseMapper<Auth> {

    /**
     * select Auth by roleId
     * @param roleId roleId
     * @return Authorization
     */
    Optional<List<Auth>> selectAuthByRoleId(@Param("roleId") Serializable roleId);

    /**
     * select Auth by userId
     * @param userId userId
     * @return Authorization
     */
    Optional<List<Auth>> selectAuthByUserId(@Param("userId") Serializable userId);
}


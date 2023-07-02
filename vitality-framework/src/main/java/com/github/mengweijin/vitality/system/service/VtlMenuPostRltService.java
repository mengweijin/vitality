package com.github.mengweijin.vitality.system.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.mengweijin.vitality.system.entity.VtlMenuPostRlt;
import com.github.mengweijin.vitality.system.mapper.VtlMenuPostRltMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 菜单-岗位关联表 服务类
 *
 * @author mengweijin
 * @since 2023-07-02
 */
@Service
public class VtlMenuPostRltService extends ServiceImpl<VtlMenuPostRltMapper, VtlMenuPostRlt> {

    @Autowired
    private VtlMenuPostRltMapper vtlMenuPostRltMapper;

}

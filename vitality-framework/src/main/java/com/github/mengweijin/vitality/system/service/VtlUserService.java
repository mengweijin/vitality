package com.github.mengweijin.vitality.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.mengweijin.vitality.system.dto.VtlUserDTO;
import com.github.mengweijin.vitality.system.entity.VtlUser;
import com.github.mengweijin.vitality.system.mapper.VtlUserMapper;
import org.dromara.hutool.core.collection.CollUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户表 服务类
 *
 * @author mengweijin
 * @since 2023-05-28
 */
@Service
public class VtlUserService extends ServiceImpl<VtlUserMapper, VtlUser> {

    @Autowired
    private VtlUserMapper vtlUserMapper;

    public IPage<VtlUserDTO> page(IPage<VtlUserDTO> page, VtlUserDTO dto){
        dto.setDeleted(false);
        return vtlUserMapper.page(page, dto);
    }

    public boolean disabledChange(Long id, boolean disabled) {
        return this.lambdaUpdate().set(VtlUser::getDisabled, disabled).eq(VtlUser::getId, id).update();
    }

    public VtlUserDTO getByIdWithoutPassword(Long id) {
        IPage<VtlUserDTO> page = new Page<>();
        page.setSize(1);
        VtlUserDTO dto = new VtlUserDTO();
        dto.setId(id);
        page = this.page(page, dto);
        if(CollUtil.isEmpty(page.getRecords())) {
            return null;
        }
        return page.getRecords().get(0);
    }
}

package com.github.mengweijin.vitality.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.mengweijin.vitality.system.dto.VtlPostDTO;
import com.github.mengweijin.vitality.system.entity.VtlPost;
import com.github.mengweijin.vitality.system.mapper.VtlPostMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 岗位管理表 服务类
 *
 * @author mengweijin
 * @since 2023-06-09
 */
@Service
public class VtlPostService extends ServiceImpl<VtlPostMapper, VtlPost> {

    @Autowired
    private VtlPostMapper vtlPostMapper;

    public VtlPostDTO detailById(Long id) {
        return vtlPostMapper.detailById(id);
    }

    public IPage<VtlPostDTO> page(IPage<VtlPostDTO> page, VtlPostDTO dto){
        return vtlPostMapper.page(page, dto);
    }

    public boolean setDisabledValue(Long id, boolean disabled) {
        return this.lambdaUpdate().set(VtlPost::getDisabled, disabled).eq(VtlPost::getId, id).update();
    }

    public List<VtlPostDTO> getByUserId(Long userId) {
        return vtlPostMapper.getByUserId(userId);
    }
}

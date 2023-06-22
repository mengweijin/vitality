package com.github.mengweijin.vitality.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.mengweijin.vitality.framework.constant.Const;
import com.github.mengweijin.vitality.system.constant.DeptConst;
import com.github.mengweijin.vitality.system.dto.VtlDeptDTO;
import com.github.mengweijin.vitality.system.entity.VtlDept;
import com.github.mengweijin.vitality.system.mapper.VtlDeptMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 部门管理表 服务类
 *
 * @author mengweijin
 * @since 2023-06-18
 */
@Service
public class VtlDeptService extends ServiceImpl<VtlDeptMapper, VtlDept> {

    @Autowired
    private VtlDeptMapper vtlDeptMapper;

    @Override
    public boolean save(VtlDept entity) {
        Long parentId = entity.getParentId();
        if(parentId == null || DeptConst.ROOT_ID == parentId) {
            entity.setParentId(DeptConst.ROOT_ID);
            entity.setAncestors(String.valueOf(DeptConst.ROOT_ID));
        } else {
            VtlDept parent = this.getById(parentId);
            entity.setParentId(parentId);
            entity.setAncestors(parent.getAncestors() + Const.SLASH + parentId);
        }
        return super.save(entity);
    }

    public VtlDeptDTO detailById(Long id) {
        return vtlDeptMapper.detailById(id);
    }

    public IPage<VtlDeptDTO> page(IPage<VtlDeptDTO> page, VtlDeptDTO dto){
        return vtlDeptMapper.page(page, dto);
    }

    public List<VtlDeptDTO> treeTableDataList(VtlDeptDTO dto) {
        return vtlDeptMapper.treeTableDataList(dto);
    }

    public boolean setDisabledValue(Long id, boolean disabled) {
        return this.lambdaUpdate().set(VtlDept::getDisabled, disabled).eq(VtlDept::getId, id).update();
    }
}

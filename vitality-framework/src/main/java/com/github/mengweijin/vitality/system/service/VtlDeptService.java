package com.github.mengweijin.vitality.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.mengweijin.vitality.framework.constant.Const;
import com.github.mengweijin.vitality.framework.exception.ClientException;
import com.github.mengweijin.vitality.framework.frontend.layui.LayuiTreeNode;
import com.github.mengweijin.vitality.system.constant.DeptConst;
import com.github.mengweijin.vitality.system.dto.VtlDeptDTO;
import com.github.mengweijin.vitality.system.entity.VtlDept;
import com.github.mengweijin.vitality.system.entity.VtlUserDeptRlt;
import com.github.mengweijin.vitality.system.mapper.VtlDeptMapper;
import org.dromara.hutool.core.collection.CollUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
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
    @Autowired
    private VtlUserDeptRltService vtlUserDeptRltService;

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

    @Override
    public boolean removeById(Serializable id) {
        Long count = vtlUserDeptRltService.lambdaQuery().eq(VtlUserDeptRlt::getDeptId, id).count();
        if(count > 0) {
            throw new ClientException("Users already exist in current department and cannot be deleted!");
        }
        List<Long> allChildrenList = this.getAllChildrenById((Long) id);
        if(CollUtil.isNotEmpty(allChildrenList)) {
            throw new ClientException("Users already exist children in the department and cannot be deleted!");
        }
        return super.removeById(id);
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

    public List<VtlDeptDTO> getByUserId(Long userId) {
        return vtlDeptMapper.getByUserId(userId);
    }

    public List<LayuiTreeNode> layuiTree() {
        List<VtlDept> deptList = this.lambdaQuery().eq(VtlDept::getDisabled, 0).list();
        List<LayuiTreeNode> list = deptList.stream().map(dept -> {
            LayuiTreeNode layuiTree = new LayuiTreeNode();
            layuiTree.setTitle(dept.getName());
            layuiTree.setId(String.valueOf(dept.getId()));
            layuiTree.setField(null);
            layuiTree.setChildren(null);
            layuiTree.setHref(null);
            layuiTree.setSpread(true);
            layuiTree.setChecked(false);
            layuiTree.setDisabled(dept.getDisabled() == 1);
            layuiTree.setParentId(String.valueOf(dept.getParentId()));
            layuiTree.setSeq(dept.getSeq());
            return layuiTree;
        }).toList();

        return LayuiTreeNode.buildTree(list, String.valueOf(DeptConst.ROOT_ID));
    }

    public List<Long> getAllChildrenById(Long id) {
        VtlDept vtlDept = this.getById(id);
        return this.lambdaQuery().select(VtlDept::getId)
                .likeRight(VtlDept::getAncestors, vtlDept.getAncestors() + Const.SLASH + vtlDept.getId())
                .list().stream().map(VtlDept::getId).toList();
    }


    public void addUsers(Long deptId, List<Long> userIdList) {
        if(CollUtil.isEmpty(userIdList)) {
            return;
        }
        List<Long> alreadyExistedUserIdList = vtlUserDeptRltService.lambdaQuery()
                .select(VtlUserDeptRlt::getUserId)
                .eq(VtlUserDeptRlt::getDeptId, deptId)
                .in(VtlUserDeptRlt::getUserId, userIdList)
                .list()
                .stream().map(VtlUserDeptRlt::getUserId).toList();

        List<VtlUserDeptRlt> userDeptRltList = userIdList.stream()
                .filter(userId -> alreadyExistedUserIdList.stream().noneMatch(existed -> existed.equals(userId)))
                .map(userId -> {
                    VtlUserDeptRlt rlt = new VtlUserDeptRlt();
                    rlt.setUserId(userId);
                    rlt.setDeptId(deptId);
                    return rlt;
                })
                .toList();

        if (CollUtil.isNotEmpty(userDeptRltList)) {
            vtlUserDeptRltService.saveBatch(userDeptRltList);
        }
    }

    public void removeUsers(Long deptId, List<Long> userIdList) {
        if(CollUtil.isEmpty(userIdList)) {
            return;
        }
        vtlUserDeptRltService.lambdaUpdate()
                .eq(VtlUserDeptRlt::getDeptId, deptId)
                .in(VtlUserDeptRlt::getUserId, userIdList)
                .remove();
    }

}

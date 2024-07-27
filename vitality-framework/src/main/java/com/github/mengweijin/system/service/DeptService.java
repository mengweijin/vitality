package com.github.mengweijin.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.mengweijin.framework.constant.Const;
import com.github.mengweijin.framework.exception.ClientException;
import com.github.mengweijin.system.constant.DeptConst;
import com.github.mengweijin.system.dto.DeptDTO;
import com.github.mengweijin.system.entity.DeptDO;
import com.github.mengweijin.system.entity.MenuDeptRltDO;
import com.github.mengweijin.system.entity.UserDeptRltDO;
import com.github.mengweijin.system.mapper.DeptMapper;
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
public class DeptService extends ServiceImpl<DeptMapper, DeptDO> {

    @Autowired
    private DeptMapper deptMapper;
    @Autowired
    private UserDeptRltService userDeptRltService;
    @Autowired
    private MenuDeptRltService menuDeptRltService;

    @Override
    public boolean save(DeptDO entity) {
        Long parentId = entity.getParentId();
        if(parentId == null || DeptConst.ROOT_ID == parentId) {
            entity.setParentId(DeptConst.ROOT_ID);
            entity.setAncestors(String.valueOf(DeptConst.ROOT_ID));
        } else {
            DeptDO parent = this.getById(parentId);
            entity.setParentId(parentId);
            entity.setAncestors(parent.getAncestors() + Const.SLASH + parentId);
        }
        return super.save(entity);
    }

    @Override
    public boolean removeById(Serializable id) {
        Long count = userDeptRltService.lambdaQuery().eq(UserDeptRltDO::getDeptId, id).count();
        if(count > 0) {
            throw new ClientException("Users already exist in current department and cannot be deleted!");
        }
        List<Long> allChildrenList = this.getAllChildrenById((Long) id);
        if(CollUtil.isNotEmpty(allChildrenList)) {
            throw new ClientException("Users already exist children in the department and cannot be deleted!");
        }
        return super.removeById(id);
    }

    public DeptDTO detailById(Long id) {
        return deptMapper.detailById(id);
    }

    public IPage<DeptDTO> page(IPage<DeptDTO> page, DeptDTO dto){
        return deptMapper.page(page, dto);
    }

    public List<DeptDTO> treeTableDataList(DeptDTO dto) {
        return deptMapper.treeTableDataList(dto);
    }

    public boolean setDisabledValue(Long id, boolean disabled) {
        return this.lambdaUpdate().set(DeptDO::getDisabled, disabled).eq(DeptDO::getId, id).update();
    }

    public List<DeptDTO> getByUserId(Long userId) {
        return deptMapper.getByUserId(userId);
    }

    public List<Long> getAllChildrenById(Long id) {
        DeptDO deptDO = this.getById(id);
        return this.lambdaQuery().select(DeptDO::getId)
                .likeRight(DeptDO::getAncestors, deptDO.getAncestors() + Const.SLASH + deptDO.getId())
                .list().stream().map(DeptDO::getId).toList();
    }


    public void addUsers(Long deptId, List<Long> userIdList) {
        if(CollUtil.isEmpty(userIdList)) {
            return;
        }
        List<Long> alreadyExistedUserIdList = userDeptRltService.lambdaQuery()
                .select(UserDeptRltDO::getUserId)
                .eq(UserDeptRltDO::getDeptId, deptId)
                .in(UserDeptRltDO::getUserId, userIdList)
                .list()
                .stream().map(UserDeptRltDO::getUserId).toList();

        List<UserDeptRltDO> userDeptRltList = userIdList.stream()
                .filter(userId -> alreadyExistedUserIdList.stream().noneMatch(existed -> existed.equals(userId)))
                .map(userId -> {
                    UserDeptRltDO rlt = new UserDeptRltDO();
                    rlt.setUserId(userId);
                    rlt.setDeptId(deptId);
                    return rlt;
                })
                .toList();

        if (CollUtil.isNotEmpty(userDeptRltList)) {
            userDeptRltService.saveBatch(userDeptRltList);
        }
    }

    public void removeUsers(Long deptId, List<Long> userIdList) {
        if(CollUtil.isEmpty(userIdList)) {
            return;
        }
        userDeptRltService.lambdaUpdate()
                .eq(UserDeptRltDO::getDeptId, deptId)
                .in(UserDeptRltDO::getUserId, userIdList)
                .remove();
    }

    public void setMenu(Long id, List<Long> menuIdList) {
        menuDeptRltService.lambdaUpdate().eq(MenuDeptRltDO::getDeptId, id).remove();
        if(CollUtil.isEmpty(menuIdList)) {
            return;
        }
        List<MenuDeptRltDO> list = menuIdList.stream().map(menuId -> new MenuDeptRltDO().setDeptId(id).setMenuId(menuId)).toList();
        menuDeptRltService.saveBatch(list);
    }
}

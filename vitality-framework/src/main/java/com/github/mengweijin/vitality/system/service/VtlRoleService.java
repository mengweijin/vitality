package com.github.mengweijin.vitality.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.mengweijin.vitality.system.dto.VtlRoleDTO;
import com.github.mengweijin.vitality.system.entity.VtlRole;
import com.github.mengweijin.vitality.system.entity.VtlUserRoleRlt;
import com.github.mengweijin.vitality.system.mapper.VtlRoleMapper;
import org.dromara.hutool.core.collection.CollUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 角色管理表 服务类
 *
 * @author mengweijin
 * @since 2023-06-09
 */
@Service
public class VtlRoleService extends ServiceImpl<VtlRoleMapper, VtlRole> {

    @Autowired
    private VtlRoleMapper vtlRoleMapper;
    @Autowired
    private VtlUserRoleRltService vtlUserRoleRltService;

    public VtlRoleDTO detailById(Long id) {
        return vtlRoleMapper.detailById(id);
    }

    public IPage<VtlRoleDTO> page(IPage<VtlRoleDTO> page, VtlRoleDTO dto){
        return vtlRoleMapper.page(page, dto);
    }

    public boolean setDisabledValue(Long id, boolean disabled) {
        return this.lambdaUpdate().set(VtlRole::getDisabled, disabled).eq(VtlRole::getId, id).update();
    }

    public List<VtlRoleDTO> getByUserId(Long userId) {
        return vtlRoleMapper.getByUserId(userId);
    }

    public void addUsers(Long roleId, List<Long> userIdList) {
        if(CollUtil.isEmpty(userIdList)) {
            return;
        }
        List<VtlUserRoleRlt> alreadyExistedRltList = vtlUserRoleRltService.lambdaQuery().eq(VtlUserRoleRlt::getRoleId, roleId).in(VtlUserRoleRlt::getUserId, userIdList).list();
        List<Long> alreadyExistedUserIdList = alreadyExistedRltList.stream().map(VtlUserRoleRlt::getUserId).toList();

        List<VtlUserRoleRlt> userRoleRltList = userIdList.stream()
                .filter(userId -> alreadyExistedUserIdList.stream().noneMatch(existed -> existed.equals(userId)))
                .map(userId -> {
                    VtlUserRoleRlt rlt = new VtlUserRoleRlt();
                    rlt.setUserId(userId);
                    rlt.setRoleId(roleId);
                    return rlt;
                })
                .toList();

        if (CollUtil.isNotEmpty(userRoleRltList)) {
            vtlUserRoleRltService.saveBatch(userRoleRltList);
        }

    }
}

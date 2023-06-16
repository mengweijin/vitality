package com.github.mengweijin.vitality.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.mengweijin.vitality.framework.constant.VitalityConst;
import com.github.mengweijin.vitality.framework.frontend.dtree.DTreeLayuiStyleDTO;
import com.github.mengweijin.vitality.framework.frontend.dtree.DTreeNode;
import com.github.mengweijin.vitality.system.dto.VtlMenuDTO;
import com.github.mengweijin.vitality.system.dto.VtlMenuTreeDataDTO;
import com.github.mengweijin.vitality.system.entity.VtlMenu;
import com.github.mengweijin.vitality.system.enums.EMenuType;
import com.github.mengweijin.vitality.system.mapper.VtlMenuMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author mengweijin
 * @date 2023/4/1
 */
@Service
public class VtlMenuService extends ServiceImpl<VtlMenuMapper, VtlMenu> {

    @Autowired
    private VtlMenuMapper vtlMenuMapper;

    public IPage<VtlMenuDTO> page(IPage<VtlMenuDTO> page, VtlMenuDTO dto){
        return vtlMenuMapper.page(page, dto);
    }

    public List<VtlMenuDTO> treeTableDataList(VtlMenuDTO dto){
        return vtlMenuMapper.treeTableDataList(dto);
    }

    public List<VtlMenuTreeDataDTO> treeLeftSideData() {
        List<VtlMenu> menuList = this.lambdaQuery().eq(VtlMenu::getDisabled, 0).in(VtlMenu::getType, EMenuType.DIR, EMenuType.MENU).list();
        List<VtlMenuTreeDataDTO> voList = Optional.ofNullable(menuList).orElse(new ArrayList<>()).stream().map(VtlMenuTreeDataDTO::new).toList();
        return VtlMenuTreeDataDTO.buildTree(voList, VitalityConst.MENU_ROOT_ID);
    }

    public List<VtlMenu> getByParentId(Long parentId) {
        return this.lambdaQuery().eq(VtlMenu::getParentId, parentId).eq(VtlMenu::getDisabled, 0).list();
    }

    public List<VtlMenu> getWithAllChildrenById(Long id) {
        VtlMenu vtlMenu = this.getById(id);
        return this.lambdaQuery().eq(VtlMenu::getDisabled, 0).likeRight(VtlMenu::getAncestors, vtlMenu.getAncestors()).list();
    }

    public boolean setDisabledValue(Long id, boolean disabled) {
        return this.lambdaUpdate().set(VtlMenu::getDisabled, disabled).eq(VtlMenu::getId, id).update();
    }

    public DTreeLayuiStyleDTO treeSelectDataList() {
        List<VtlMenu> menuList = this.lambdaQuery().eq(VtlMenu::getDisabled, 0).list();
        List<DTreeNode> voList = Optional.ofNullable(menuList).orElse(new ArrayList<>()).stream().map(DTreeNode::new).toList();
        List<DTreeNode> dataList = DTreeNode.buildTree(voList, String.valueOf(VitalityConst.MENU_ROOT_ID));
        return new DTreeLayuiStyleDTO(dataList);
    }
}

package com.github.mengweijin.vitality.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.mengweijin.vitality.framework.constant.Const;
import com.github.mengweijin.vitality.framework.constant.VitalityConst;
import com.github.mengweijin.vitality.system.dto.VtlMenuDTO;
import com.github.mengweijin.vitality.system.dto.VtlMenuTreeDataDTO;
import com.github.mengweijin.vitality.system.entity.VtlMenu;
import com.github.mengweijin.vitality.system.enums.EMenuType;
import com.github.mengweijin.vitality.system.mapper.VtlMenuMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author mengweijin
 * @date 2023/4/1
 */
@Service
public class VtlMenuService extends ServiceImpl<VtlMenuMapper, VtlMenu> {

    @Autowired
    private VtlMenuMapper vtlMenuMapper;

    @Override
    public boolean save(VtlMenu entity) {
        Long parentId = entity.getParentId();
        if(parentId == null || VitalityConst.MENU_ROOT_ID == parentId) {
            entity.setParentId(VitalityConst.MENU_ROOT_ID);
            entity.setAncestors(String.valueOf(VitalityConst.MENU_ROOT_ID));
        } else {
            VtlMenu parentMenu = this.getById(parentId);
            entity.setParentId(parentId);
            entity.setAncestors(parentMenu.getAncestors() + Const.SLASH + parentId);
        }
        return super.save(entity);
    }

    public IPage<VtlMenuDTO> page(IPage<VtlMenuDTO> page, VtlMenuDTO dto){
        return vtlMenuMapper.page(page, dto);
    }

    public List<VtlMenuDTO> treeTableDataList(VtlMenuDTO dto) {
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

    public List<Long> getMenuIdWithAllParentById(Long id) {
        VtlMenu vtlMenu = this.getById(id);
        String ancestors = vtlMenu.getAncestors();
        return Arrays.stream(ancestors.split(Const.SLASH)).map(Long::valueOf).toList();
    }

    public List<Long> getMenuIdWithAllChildrenById(Long id) {
        VtlMenu vtlMenu = this.getById(id);
        return this.lambdaQuery().select(VtlMenu::getId)
                .likeRight(VtlMenu::getAncestors, vtlMenu.getAncestors())
                .list().stream().map(VtlMenu::getId).toList();
    }

    public List<VtlMenu> getWithAllChildrenByAncestors(String ancestors) {
        return this.lambdaQuery().likeRight(VtlMenu::getAncestors, ancestors).list();
    }

    public boolean setDisabledValue(Long id, boolean disabled) {
        return this.lambdaUpdate().set(VtlMenu::getDisabled, disabled).eq(VtlMenu::getId, id).update();
    }

    public String titleHierarchyById(Long id) {
        String titleHierarchy;
        if(VitalityConst.MENU_ROOT_ID == id) {
            return Const.EMPTY;
        }
        VtlMenu menu = this.getById(id);
        String ancestors = menu.getAncestors();

        titleHierarchy = this.lambdaQuery().select(VtlMenu::getTitle)
                .in(VtlMenu::getId, Arrays.asList(ancestors.split(Const.SLASH)))
                .list().stream()
                .map(VtlMenu::getTitle)
                .collect(Collectors.joining(Const.SLASH));

        return titleHierarchy + Const.SLASH + menu.getTitle();
    }
}

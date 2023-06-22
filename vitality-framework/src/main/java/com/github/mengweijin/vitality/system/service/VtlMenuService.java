package com.github.mengweijin.vitality.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.mengweijin.vitality.framework.constant.Const;
import com.github.mengweijin.vitality.system.constant.MenuConst;
import com.github.mengweijin.vitality.system.dto.VtlMenuDTO;
import com.github.mengweijin.vitality.system.dto.VtlMenuTreeDataDTO;
import com.github.mengweijin.vitality.system.entity.VtlMenu;
import com.github.mengweijin.vitality.system.enums.EMenuType;
import com.github.mengweijin.vitality.system.mapper.VtlMenuMapper;
import org.dromara.hutool.core.collection.CollUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
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
        if(parentId == null || MenuConst.ROOT_ID == parentId) {
            entity.setParentId(MenuConst.ROOT_ID);
            entity.setAncestors(String.valueOf(MenuConst.ROOT_ID));
        } else {
            VtlMenu parentMenu = this.getById(parentId);
            entity.setParentId(parentId);
            entity.setAncestors(parentMenu.getAncestors() + Const.SLASH + parentId);
        }
        return super.save(entity);
    }

    @Override
    public boolean updateById(VtlMenu entity) {
        return super.updateById(entity);
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
        return VtlMenuTreeDataDTO.buildTree(voList, MenuConst.ROOT_ID);
    }

    public List<VtlMenu> getByParentId(Long parentId) {
        return this.lambdaQuery().eq(VtlMenu::getParentId, parentId).eq(VtlMenu::getDisabled, 0).list();
    }

    public List<Long> getAllParentById(Long id) {
        VtlMenu vtlMenu = this.getById(id);
        String ancestors = vtlMenu.getAncestors();
        return Arrays.stream(ancestors.split(Const.SLASH)).map(Long::valueOf).toList();
    }

    public List<Long> getAllChildrenById(Long id) {
        VtlMenu vtlMenu = this.getById(id);
        return this.lambdaQuery().select(VtlMenu::getId)
                .likeRight(VtlMenu::getAncestors, vtlMenu.getAncestors() + Const.SLASH + vtlMenu.getId())
                .list().stream().map(VtlMenu::getId).toList();
    }

    public boolean setDisabledValue(Long id, boolean disabled) {
        return this.lambdaUpdate().set(VtlMenu::getDisabled, disabled).eq(VtlMenu::getId, id).update();
    }

    public String titleHierarchyById(Long id) {
        String titleHierarchy;
        if(MenuConst.ROOT_ID == id) {
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

    public List<VtlMenu> getAllChildrenByTitle(String title) {
        List<Long> idList = this.lambdaQuery().select(VtlMenu::getId).like(VtlMenu::getTitle, title).list().stream().map(VtlMenu::getId).toList();
        List<VtlMenu> allList = this.list();
        List<VtlMenu> resultList = new ArrayList<>();
        for (Long id : idList) {
            resultList.addAll(this.findAllChildrenByParentId(allList, id));
        }
        resultList = CollUtil.distinct(resultList, VtlMenu::getId, true);
        resultList.sort(Comparator.comparingInt(VtlMenu::getSeq));
        return resultList;
    }


    public List<VtlMenu> getAllChildrenByParentId(Long parentId) {
        List<VtlMenu> menuList = this.list();
        return this.findAllChildrenByParentId(menuList, parentId);
    }

    public List<VtlMenu> findAllChildrenByParentId(List<VtlMenu> list, Long parentId) {
        Map<Long, List<VtlMenu>> groupedList = list.stream().collect(Collectors.groupingBy(VtlMenu::getParentId));
        List<VtlMenu> resultList = new ArrayList<>();
        this.recursiveSearchChildren(groupedList, parentId, resultList);
        resultList.sort(Comparator.comparingInt(VtlMenu::getSeq));
        return resultList;
    }

    private void recursiveSearchChildren(Map<Long, List<VtlMenu>> groupedList, Long id, List<VtlMenu> resultList) {
        List<VtlMenu> menuList = groupedList.get(id);
        if(menuList != null && !menuList.isEmpty()) {
            resultList.addAll(menuList);
            for (VtlMenu menu : menuList) {
                recursiveSearchChildren(groupedList, menu.getId(), resultList);
            }
        }
    }

    public List<VtlMenu> findAllParentById(List<VtlMenu> list, Long id) {
        List<VtlMenu> resultList = new ArrayList<>();
        Long rootId = this.recursiveSearchParent(list, id, resultList);
        resultList.sort(Comparator.comparingInt(VtlMenu::getSeq));
        return resultList;
    }

    /**
     * all parent and current id node
     * @param list all list data
     * @param id id
     * @param resultList result list
     * @return rootId
     */
    private Long recursiveSearchParent(List<VtlMenu> list, Long id, List<VtlMenu> resultList) {
        Optional<VtlMenu> currentNodeOptional = list.stream().filter(m -> m.getId().equals(id)).findFirst();
        if(currentNodeOptional.isPresent()) {
            resultList.add(currentNodeOptional.get());
            return this.recursiveSearchParent(list, currentNodeOptional.get().getParentId(), resultList);
        } else {
            return id;
        }
    }
}

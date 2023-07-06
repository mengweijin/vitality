package com.github.mengweijin.vitality.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.mengweijin.vitality.framework.constant.Const;
import com.github.mengweijin.vitality.system.constant.MenuConst;
import com.github.mengweijin.vitality.system.constant.RoleConst;
import com.github.mengweijin.vitality.system.constant.UserConst;
import com.github.mengweijin.vitality.system.dto.VtlMenuDTO;
import com.github.mengweijin.vitality.system.dto.VtlMenuTreeDataDTO;
import com.github.mengweijin.vitality.system.dto.VtlRoleDTO;
import com.github.mengweijin.vitality.system.dto.VtlUserDetailDTO;
import com.github.mengweijin.vitality.system.entity.VtlMenu;
import com.github.mengweijin.vitality.system.entity.VtlMenuDeptRlt;
import com.github.mengweijin.vitality.system.entity.VtlMenuPostRlt;
import com.github.mengweijin.vitality.system.entity.VtlMenuRoleRlt;
import com.github.mengweijin.vitality.system.entity.VtlMenuUserRlt;
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
    @Autowired
    private VtlRoleService vtlRoleService;
    @Autowired
    private VtlDeptService vtlDeptService;
    @Autowired
    private VtlPostService vtlPostService;
    @Autowired
    private VtlUserService vtlUserService;
    @Autowired
    private VtlMenuRoleRltService vtlMenuRoleRltService;
    @Autowired
    private VtlMenuDeptRltService vtlMenuDeptRltService;
    @Autowired
    private VtlMenuPostRltService vtlMenuPostRltService;
    @Autowired
    private VtlMenuUserRltService vtlMenuUserRltService;

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
        List<VtlMenu> menuList;
        Long loginUserId = vtlUserService.getSessionUser().getId();
        VtlUserDetailDTO vtlUserDetailDTO = vtlUserService.detailById(loginUserId);
        List<VtlRoleDTO> roleList = vtlUserDetailDTO.getRoleList();
        if(UserConst.ADMIN_ID == loginUserId || roleList.stream().anyMatch(role -> RoleConst.ADMIN.equals(role.getCode()))) {
            menuList = this.lambdaQuery().in(VtlMenu::getType, EMenuType.DIR, EMenuType.MENU).list();
        } else {
            menuList = this.getMenuByLoginUser(loginUserId);
            menuList = menuList.stream()
                    .filter(menu -> menu.getDisabled() == 0 && (EMenuType.DIR == menu.getType() || EMenuType.MENU == menu.getType()))
                    .toList();
        }

        List<VtlMenuTreeDataDTO> voList = Optional.ofNullable(menuList).orElse(new ArrayList<>()).stream().map(VtlMenuTreeDataDTO::new).toList();
        return VtlMenuTreeDataDTO.buildTree(voList, MenuConst.ROOT_ID);
    }

    public List<VtlMenu> getMenuByLoginUser(Long userId) {
        List<VtlRoleDTO> roleList = vtlRoleService.getByUserId(userId);
        if(UserConst.ADMIN_ID == userId || roleList.stream().anyMatch(role -> RoleConst.ADMIN.equals(role.getCode()))) {
            return this.lambdaQuery().list();
        }

        List<Long> menuIdListByDept = vtlDeptService.getByUserId(userId).stream().flatMap(deptDTO -> this.byDept(deptDTO.getId()).stream()).toList();
        List<Long> menuIdListByRole = vtlRoleService.getByUserId(userId).stream().flatMap(roleDTO -> this.byRole(roleDTO.getId()).stream()).toList();
        List<Long> menuIdListByPost = vtlPostService.getByUserId(userId).stream().flatMap(postDTO -> this.byPost(postDTO.getId()).stream()).toList();
        List<Long> menuIdListByUser = this.byUser(userId);

        List<Long> allMenuIdList = new ArrayList<>();
        allMenuIdList.addAll(menuIdListByDept);
        allMenuIdList.addAll(menuIdListByRole);
        allMenuIdList.addAll(menuIdListByPost);
        allMenuIdList.addAll(menuIdListByUser);
        allMenuIdList = allMenuIdList.stream().distinct().toList();
        if(CollUtil.isEmpty(allMenuIdList)) {
            return new ArrayList<>();
        }
        return this.lambdaQuery().in(VtlMenu::getId, allMenuIdList).list();
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

    public List<Long> byRole(Long roleId) {
        return vtlMenuRoleRltService.lambdaQuery()
                .select(VtlMenuRoleRlt::getMenuId)
                .eq(VtlMenuRoleRlt::getRoleId, roleId)
                .list()
                .stream().map(VtlMenuRoleRlt::getMenuId).toList();
    }

    public List<Long> byDept(Long deptId) {
        return vtlMenuDeptRltService.lambdaQuery()
                .select(VtlMenuDeptRlt::getMenuId)
                .eq(VtlMenuDeptRlt::getDeptId, deptId)
                .list()
                .stream().map(VtlMenuDeptRlt::getMenuId).toList();
    }

    public List<Long> byPost(Long postId) {
        return vtlMenuPostRltService.lambdaQuery()
                .select(VtlMenuPostRlt::getMenuId)
                .eq(VtlMenuPostRlt::getPostId, postId)
                .list()
                .stream().map(VtlMenuPostRlt::getMenuId).toList();
    }

    public List<Long> byUser(Long userId) {
        return vtlMenuUserRltService.lambdaQuery()
                .select(VtlMenuUserRlt::getMenuId)
                .eq(VtlMenuUserRlt::getUserId, userId)
                .list()
                .stream().map(VtlMenuUserRlt::getMenuId).toList();
    }
}

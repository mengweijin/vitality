package com.github.mengweijin.vitality.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.mengweijin.vitality.framework.constant.Const;
import com.github.mengweijin.vitality.system.constant.MenuConst;
import com.github.mengweijin.vitality.system.constant.RoleConst;
import com.github.mengweijin.vitality.system.constant.UserConst;
import com.github.mengweijin.vitality.system.dto.MenuDTO;
import com.github.mengweijin.vitality.system.dto.MenuTreeDataDTO;
import com.github.mengweijin.vitality.system.dto.RoleDTO;
import com.github.mengweijin.vitality.system.dto.UserDetailDTO;
import com.github.mengweijin.vitality.system.entity.MenuDO;
import com.github.mengweijin.vitality.system.entity.MenuDeptRltDO;
import com.github.mengweijin.vitality.system.entity.MenuPostRltDO;
import com.github.mengweijin.vitality.system.entity.MenuRoleRltDO;
import com.github.mengweijin.vitality.system.entity.MenuUserRltDO;
import com.github.mengweijin.vitality.system.enums.EMenuType;
import com.github.mengweijin.vitality.system.mapper.MenuMapper;
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
public class MenuService extends ServiceImpl<MenuMapper, MenuDO> {

    @Autowired
    private MenuMapper menuMapper;
    @Autowired
    private RoleService roleService;
    @Autowired
    private DeptService deptService;
    @Autowired
    private PostService postService;
    @Autowired
    private UserService userService;
    @Autowired
    private MenuRoleRltService menuRoleRltService;
    @Autowired
    private MenuDeptRltService menuDeptRltService;
    @Autowired
    private MenuPostRltService menuPostRltService;
    @Autowired
    private MenuUserRltService menuUserRltService;

    @Override
    public boolean save(MenuDO entity) {
        Long parentId = entity.getParentId();
        if(parentId == null || MenuConst.ROOT_ID == parentId) {
            entity.setParentId(MenuConst.ROOT_ID);
            entity.setAncestors(String.valueOf(MenuConst.ROOT_ID));
        } else {
            MenuDO parentMenu = this.getById(parentId);
            entity.setParentId(parentId);
            entity.setAncestors(parentMenu.getAncestors() + Const.SLASH + parentId);
        }
        return super.save(entity);
    }

    @Override
    public boolean updateById(MenuDO entity) {
        return super.updateById(entity);
    }

    public IPage<MenuDTO> page(IPage<MenuDTO> page, MenuDTO dto){
        return menuMapper.page(page, dto);
    }

    public List<MenuDTO> treeTableDataList(MenuDTO dto) {
        return menuMapper.treeTableDataList(dto);
    }

    public List<MenuTreeDataDTO> treeLeftSideData() {
        List<MenuDO> menuList;
        Long loginUserId = userService.getSessionUser().getId();
        UserDetailDTO vtlUserDetailDTO = userService.detailById(loginUserId);
        List<RoleDTO> roleList = vtlUserDetailDTO.getRoleList();
        if(UserConst.ADMIN_ID == loginUserId || roleList.stream().anyMatch(role -> RoleConst.ADMIN.equals(role.getCode()))) {
            menuList = this.lambdaQuery().in(MenuDO::getType, EMenuType.DIR, EMenuType.MENU).list();
        } else {
            menuList = this.getMenuByLoginUser(loginUserId);
            menuList = menuList.stream()
                    .filter(menu -> menu.getDisabled() == 0 && (EMenuType.DIR == menu.getType() || EMenuType.MENU == menu.getType()))
                    .toList();
        }

        List<MenuTreeDataDTO> voList = Optional.ofNullable(menuList).orElse(new ArrayList<>()).stream().map(MenuTreeDataDTO::new).toList();
        return MenuTreeDataDTO.buildTree(voList, MenuConst.ROOT_ID);
    }

    public List<MenuDO> getMenuByLoginUser(Long userId) {
        List<RoleDTO> roleList = roleService.getByUserId(userId);
        if(UserConst.ADMIN_ID == userId || roleList.stream().anyMatch(role -> RoleConst.ADMIN.equals(role.getCode()))) {
            return this.lambdaQuery().list();
        }

        List<Long> menuIdListByDept = deptService.getByUserId(userId).stream().flatMap(deptDTO -> this.byDept(deptDTO.getId()).stream()).toList();
        List<Long> menuIdListByRole = roleService.getByUserId(userId).stream().flatMap(roleDTO -> this.byRole(roleDTO.getId()).stream()).toList();
        List<Long> menuIdListByPost = postService.getByUserId(userId).stream().flatMap(postDTO -> this.byPost(postDTO.getId()).stream()).toList();
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
        return this.lambdaQuery().in(MenuDO::getId, allMenuIdList).list();
    }

    public List<MenuDO> getByParentId(Long parentId) {
        return this.lambdaQuery().eq(MenuDO::getParentId, parentId).eq(MenuDO::getDisabled, 0).list();
    }

    public List<Long> getAllParentById(Long id) {
        MenuDO menuDO = this.getById(id);
        String ancestors = menuDO.getAncestors();
        return Arrays.stream(ancestors.split(Const.SLASH)).map(Long::valueOf).toList();
    }

    public List<Long> getAllChildrenById(Long id) {
        MenuDO menuDO = this.getById(id);
        return this.lambdaQuery().select(MenuDO::getId)
                .likeRight(MenuDO::getAncestors, menuDO.getAncestors() + Const.SLASH + menuDO.getId())
                .list().stream().map(MenuDO::getId).toList();
    }

    public boolean setDisabledValue(Long id, boolean disabled) {
        return this.lambdaUpdate().set(MenuDO::getDisabled, disabled).eq(MenuDO::getId, id).update();
    }

    public String titleHierarchyById(Long id) {
        String titleHierarchy;
        if(MenuConst.ROOT_ID == id) {
            return Const.EMPTY;
        }
        MenuDO menu = this.getById(id);
        String ancestors = menu.getAncestors();

        titleHierarchy = this.lambdaQuery().select(MenuDO::getTitle)
                .in(MenuDO::getId, Arrays.asList(ancestors.split(Const.SLASH)))
                .list().stream()
                .map(MenuDO::getTitle)
                .collect(Collectors.joining(Const.SLASH));

        return titleHierarchy + Const.SLASH + menu.getTitle();
    }

    public List<MenuDO> getAllChildrenByTitle(String title) {
        List<Long> idList = this.lambdaQuery().select(MenuDO::getId).like(MenuDO::getTitle, title).list().stream().map(MenuDO::getId).toList();
        List<MenuDO> allList = this.list();
        List<MenuDO> resultList = new ArrayList<>();
        for (Long id : idList) {
            resultList.addAll(this.findAllChildrenByParentId(allList, id));
        }
        resultList = CollUtil.distinct(resultList, MenuDO::getId, true);
        resultList.sort(Comparator.comparingInt(MenuDO::getSeq));
        return resultList;
    }


    public List<MenuDO> getAllChildrenByParentId(Long parentId) {
        List<MenuDO> menuList = this.list();
        return this.findAllChildrenByParentId(menuList, parentId);
    }

    public List<MenuDO> findAllChildrenByParentId(List<MenuDO> list, Long parentId) {
        Map<Long, List<MenuDO>> groupedList = list.stream().collect(Collectors.groupingBy(MenuDO::getParentId));
        List<MenuDO> resultList = new ArrayList<>();
        this.recursiveSearchChildren(groupedList, parentId, resultList);
        resultList.sort(Comparator.comparingInt(MenuDO::getSeq));
        return resultList;
    }

    private void recursiveSearchChildren(Map<Long, List<MenuDO>> groupedList, Long id, List<MenuDO> resultList) {
        List<MenuDO> menuList = groupedList.get(id);
        if(menuList != null && !menuList.isEmpty()) {
            resultList.addAll(menuList);
            for (MenuDO menu : menuList) {
                recursiveSearchChildren(groupedList, menu.getId(), resultList);
            }
        }
    }

    public List<MenuDO> findAllParentById(List<MenuDO> list, Long id) {
        List<MenuDO> resultList = new ArrayList<>();
        Long rootId = this.recursiveSearchParent(list, id, resultList);
        resultList.sort(Comparator.comparingInt(MenuDO::getSeq));
        return resultList;
    }

    /**
     * all parent and current id node
     * @param list all list data
     * @param id id
     * @param resultList result list
     * @return rootId
     */
    private Long recursiveSearchParent(List<MenuDO> list, Long id, List<MenuDO> resultList) {
        Optional<MenuDO> currentNodeOptional = list.stream().filter(m -> m.getId().equals(id)).findFirst();
        if(currentNodeOptional.isPresent()) {
            resultList.add(currentNodeOptional.get());
            return this.recursiveSearchParent(list, currentNodeOptional.get().getParentId(), resultList);
        } else {
            return id;
        }
    }

    public List<Long> byRole(Long roleId) {
        return menuRoleRltService.lambdaQuery()
                .select(MenuRoleRltDO::getMenuId)
                .eq(MenuRoleRltDO::getRoleId, roleId)
                .list()
                .stream().map(MenuRoleRltDO::getMenuId).toList();
    }

    public List<Long> byDept(Long deptId) {
        return menuDeptRltService.lambdaQuery()
                .select(MenuDeptRltDO::getMenuId)
                .eq(MenuDeptRltDO::getDeptId, deptId)
                .list()
                .stream().map(MenuDeptRltDO::getMenuId).toList();
    }

    public List<Long> byPost(Long postId) {
        return menuPostRltService.lambdaQuery()
                .select(MenuPostRltDO::getMenuId)
                .eq(MenuPostRltDO::getPostId, postId)
                .list()
                .stream().map(MenuPostRltDO::getMenuId).toList();
    }

    public List<Long> byUser(Long userId) {
        return menuUserRltService.lambdaQuery()
                .select(MenuUserRltDO::getMenuId)
                .eq(MenuUserRltDO::getUserId, userId)
                .list()
                .stream().map(MenuUserRltDO::getMenuId).toList();
    }
}

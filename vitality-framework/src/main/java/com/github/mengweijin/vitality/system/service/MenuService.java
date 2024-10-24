package com.github.mengweijin.vitality.system.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.repository.CrudRepository;
import com.github.mengweijin.vitality.framework.constant.Const;
import com.github.mengweijin.vitality.system.constant.MenuConst;
import com.github.mengweijin.vitality.system.constant.UserConst;
import com.github.mengweijin.vitality.system.domain.entity.Menu;
import com.github.mengweijin.vitality.system.domain.pure.PureAsyncRoutes;
import com.github.mengweijin.vitality.system.enums.EMenuType;
import com.github.mengweijin.vitality.system.mapper.MenuMapper;
import com.github.mengweijin.vitality.system.mapper.RoleMapper;
import com.github.mengweijin.vitality.system.mapper.RoleMenuMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dromara.hutool.core.text.StrUtil;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * <p>
 * Menu Service
 * Add @Transactional(rollbackFor = Exception.class) if you need.
 * </p>
 *
 * @author mengweijin
 * @since 2023-06-03
 */
@Slf4j
@Service
@AllArgsConstructor
public class MenuService extends CrudRepository<MenuMapper, Menu> {

    private RoleMapper roleMapper;

    private RoleMenuMapper roleMenuMapper;

    /**
     * Custom paging query
     *
     * @param page page
     * @param menu {@link Menu}
     * @return IPage
     */
    public IPage<Menu> page(IPage<Menu> page, Menu menu) {
        LambdaQueryWrapper<Menu> query = new LambdaQueryWrapper<>();
        query
                .eq(!Objects.isNull(menu.getParentId()), Menu::getParentId, menu.getParentId())
                .eq(StrUtil.isNotBlank(menu.getType()), Menu::getType, menu.getType())
                .eq(StrUtil.isNotBlank(menu.getTitle()), Menu::getTitle, menu.getTitle())
                .eq(StrUtil.isNotBlank(menu.getRouterName()), Menu::getRouterName, menu.getRouterName())
                .eq(StrUtil.isNotBlank(menu.getRouterPath()), Menu::getRouterPath, menu.getRouterPath())
                .eq(StrUtil.isNotBlank(menu.getComponentPath()), Menu::getComponentPath, menu.getComponentPath())
                .eq(!Objects.isNull(menu.getSeq()), Menu::getSeq, menu.getSeq())
                .eq(StrUtil.isNotBlank(menu.getRedirect()), Menu::getRedirect, menu.getRedirect())
                .eq(StrUtil.isNotBlank(menu.getIcon()), Menu::getIcon, menu.getIcon())
                .eq(StrUtil.isNotBlank(menu.getExtraIcon()), Menu::getExtraIcon, menu.getExtraIcon())
                .eq(StrUtil.isNotBlank(menu.getEnterTransition()), Menu::getEnterTransition, menu.getEnterTransition())
                .eq(StrUtil.isNotBlank(menu.getLeaveTransition()), Menu::getLeaveTransition, menu.getLeaveTransition())
                .eq(StrUtil.isNotBlank(menu.getActivePath()), Menu::getActivePath, menu.getActivePath())
                .eq(StrUtil.isNotBlank(menu.getPermission()), Menu::getPermission, menu.getPermission())
                .eq(StrUtil.isNotBlank(menu.getIframeSrc()), Menu::getIframeSrc, menu.getIframeSrc())
                .eq(StrUtil.isNotBlank(menu.getIframeLoading()), Menu::getIframeLoading, menu.getIframeLoading())
                .eq(StrUtil.isNotBlank(menu.getKeepAlive()), Menu::getKeepAlive, menu.getKeepAlive())
                .eq(StrUtil.isNotBlank(menu.getHiddenTag()), Menu::getHiddenTag, menu.getHiddenTag())
                .eq(StrUtil.isNotBlank(menu.getFixedTag()), Menu::getFixedTag, menu.getFixedTag())
                .eq(StrUtil.isNotBlank(menu.getShowLink()), Menu::getShowLink, menu.getShowLink())
                .eq(StrUtil.isNotBlank(menu.getShowParent()), Menu::getShowParent, menu.getShowParent())
                .eq(!Objects.isNull(menu.getId()), Menu::getId, menu.getId())
                .eq(!Objects.isNull(menu.getCreateBy()), Menu::getCreateBy, menu.getCreateBy())
                .eq(!Objects.isNull(menu.getCreateTime()), Menu::getCreateTime, menu.getCreateTime())
                .eq(!Objects.isNull(menu.getUpdateBy()), Menu::getUpdateBy, menu.getUpdateBy())
                .eq(!Objects.isNull(menu.getUpdateTime()), Menu::getUpdateTime, menu.getUpdateTime());
        return this.page(page, query);
    }

    public Set<String> getMenuPermissionListByLoginUsername(String username) {
        if (UserConst.ADMIN_USERNAME.equals(username)) {
            return Collections.singleton(Const.ALL_PERMISSIONS);
        }
        return this.getBaseMapper().selectPermissionListByUsername(username);
    }

    public PureAsyncRoutes getAsyncRoutes() {
        List<Menu> list = this.lambdaQuery()
                .ne(Menu::getType, EMenuType.BTN.getValue())
                .orderByAsc(Menu::getSeq)
                .list();
        return PureAsyncRoutes.tree(list, MenuConst.ROOT_ID);
    }

    public List<Long> getMenuIdsByRoleId(Long roleId) {
        return roleMenuMapper.selectMenuIdsByRoleId(roleId);
    }

}

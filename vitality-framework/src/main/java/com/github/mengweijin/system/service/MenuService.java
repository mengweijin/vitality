package com.github.mengweijin.system.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.mengweijin.system.constant.UserConst;
import com.github.mengweijin.system.domain.entity.Menu;
import com.github.mengweijin.system.mapper.MenuMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dromara.hutool.core.text.StrUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * <p>
 *  Menu Service
 *  Add @Transactional(rollbackFor = Exception.class) if you need.
 * </p>
 *
 * @author mengweijin
 * @since 2023-06-03
 */
@Slf4j
@Service
@AllArgsConstructor
public class MenuService extends ServiceImpl<MenuMapper, Menu> {

    private RoleService roleService;

    /**
     * Custom paging query
     * @param page page
     * @param menu {@link Menu}
     * @return IPage
     */
    public IPage<Menu> page(IPage<Menu> page, Menu menu){
        LambdaQueryWrapper<Menu> query = new LambdaQueryWrapper<>();
        query
                .eq(!Objects.isNull(menu.getParentId()), Menu::getParentId, menu.getParentId())
                .eq(StrUtil.isNotBlank(menu.getTitle()), Menu::getTitle, menu.getTitle())
                .eq(StrUtil.isNotBlank(menu.getType()), Menu::getType, menu.getType())
                .eq(StrUtil.isNotBlank(menu.getPermission()), Menu::getPermission, menu.getPermission())
                .eq(!Objects.isNull(menu.getSeq()), Menu::getSeq, menu.getSeq())
                .eq(StrUtil.isNotBlank(menu.getIcon()), Menu::getIcon, menu.getIcon())
                .eq(StrUtil.isNotBlank(menu.getUrl()), Menu::getUrl, menu.getUrl())
                .eq(StrUtil.isNotBlank(menu.getDisabled()), Menu::getDisabled, menu.getDisabled())
                .eq(StrUtil.isNotBlank(menu.getRemark()), Menu::getRemark, menu.getRemark())
                .eq(!Objects.isNull(menu.getId()), Menu::getId, menu.getId())
                .eq(!Objects.isNull(menu.getCreateBy()), Menu::getCreateBy, menu.getCreateBy())
                .eq(!Objects.isNull(menu.getCreateTime()), Menu::getCreateTime, menu.getCreateTime())
                .eq(!Objects.isNull(menu.getUpdateBy()), Menu::getUpdateBy, menu.getUpdateBy())
                .eq(!Objects.isNull(menu.getUpdateTime()), Menu::getUpdateTime, menu.getUpdateTime());
        return this.page(page, query);
    }

    public List<String> getMenuPermissionListByLoginUsername(String username) {
        if(UserConst.ADMIN_LOGIN_NAME.equals(username)) {
            return this.lambdaQuery().select(Menu::getPermission).list().stream().map(Menu::getPermission).toList();
        }
        return this.getBaseMapper().selectPermissionListByUsername(username);
    }
}

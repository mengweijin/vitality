package com.github.mengweijin.vitality.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.mengweijin.vitality.framework.domain.R;
import com.github.mengweijin.vitality.system.domain.entity.Menu;
import com.github.mengweijin.vitality.system.service.MenuService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

/**
 * <p>
 *  Menu Controller
 * </p>
 *
 * @author mengweijin
 * @since 2023-06-03
 */
@Slf4j
@AllArgsConstructor
@Validated
@RestController
@RequestMapping("/system/menu")
public class MenuController {

    private MenuService menuService;

    /**
     * <p>
     * Get Menu page by Menu
     * </p>
     * @param page page
     * @param menu {@link Menu}
     * @return Page<Menu>
     */
    @SaCheckPermission("system:menu:query")
    @GetMapping("/page")
    public IPage<Menu> page(Page<Menu> page, Menu menu) {
        return menuService.page(page, menu);
    }

    /**
     * <p>
     * Get Menu list by Menu
     * </p>
     * @param menu {@link Menu}
     * @return List<Menu>
     */
    @SaCheckPermission("system:menu:query")
    @GetMapping("/list")
    public List<Menu> list(Menu menu) {
        return menuService.list(new QueryWrapper<>(menu));
    }

    /**
     * <p>
     * Get Menu by id
     * </p>
     * @param id id
     * @return Menu
     */
    @SaCheckPermission("system:menu:query")
    @GetMapping("/{id}")
    public Menu getById(@PathVariable("id") Long id) {
        return menuService.getById(id);
    }

    /**
     * <p>
     * Add Menu
     * </p>
     * @param menu {@link Menu}
     */
    @SaCheckPermission("system:menu:create")
    @PostMapping
    public R<Void> add(@Valid @RequestBody Menu menu) {
        boolean bool = menuService.save(menu);
        return R.ajax(bool);
    }

    /**
     * <p>
     * Update Menu
     * </p>
     * @param menu {@link Menu}
     */
    @SaCheckPermission("system:menu:update")
    @PutMapping
    public R<Void> update(@Valid @RequestBody Menu menu) {
        boolean bool = menuService.updateById(menu);
        return R.ajax(bool);
    }

    /**
     * <p>
     * Delete Menu by id(s), Multiple ids can be separated by commas ",".
     * </p>
     * @param ids id
     */
    @SaCheckPermission("system:menu:delete")
    @DeleteMapping("/{ids}")
    public R<Void> delete(@PathVariable("ids") Long[] ids) {
        int i = menuService.getBaseMapper().deleteByIds(Arrays.asList(ids));
        return R.ajax(i);
    }

}


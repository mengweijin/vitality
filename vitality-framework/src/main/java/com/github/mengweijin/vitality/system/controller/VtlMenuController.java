package com.github.mengweijin.vitality.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.mengweijin.vitality.framework.domain.R;
import com.github.mengweijin.vitality.system.dto.VtlMenuDTO;
import com.github.mengweijin.vitality.system.entity.VtlMenu;
import com.github.mengweijin.vitality.system.service.VtlMenuService;
import com.github.mengweijin.vitality.system.dto.VtlMenuTreeDataDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

/**
 * @author mengweijin
 * @date 2023/4/1
 */
@RestController
@RequestMapping("/vtl-menu")
public class VtlMenuController {
    @Autowired
    private VtlMenuService menuService;

    @PostMapping
    public R add(VtlMenu vtlMenu) {
        boolean bool = menuService.save(vtlMenu);
        return R.bool(bool);
    }

    @PutMapping
    public R edit(VtlMenu vtlMenu) {
        boolean bool = menuService.updateById(vtlMenu);
        return R.bool(bool);
    }

    @DeleteMapping("/{id}")
    public R delete(@PathVariable("id") Long id) {
        boolean bool = menuService.removeById(id);
        return R.bool(bool);
    }

    @DeleteMapping
    public R delete(Long[] ids) {
        boolean bool = menuService.removeBatchByIds(Arrays.asList(ids));
        return R.bool(bool);
    }

    @GetMapping("/{id}")
    public VtlMenu getById(@PathVariable("id") Long id) {
        return menuService.getById(id);
    }

    @GetMapping("/page")
    public IPage<VtlMenuDTO> page(Page<VtlMenuDTO> page, VtlMenuDTO dto) {
        return menuService.page(page, dto);
    }

    @GetMapping("/tree")
    public List<VtlMenuTreeDataDTO> tree() {
        return menuService.tree();
    }
}

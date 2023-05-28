package com.github.mengweijin.vitality.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.mengweijin.vitality.framework.domain.R;
import com.github.mengweijin.vitality.framework.mvc.BaseController;
import com.github.mengweijin.vitality.system.dto.VtlUserDTO;
import com.github.mengweijin.vitality.system.entity.VtlUser;
import com.github.mengweijin.vitality.system.service.VtlUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Arrays;

/**
 * 用户表 控制器
 *
 * @author mengweijin
 * @since 2023-05-28
 */
@RestController
@RequestMapping("/vtl-user")
public class VtlUserController extends BaseController {

    @Autowired
    private VtlUserService vtlUserService;

    @PostMapping
    public R add(VtlUser vtlUser) {
        boolean bool = vtlUserService.save(vtlUser);
        return R.bool(bool);
    }

    @PutMapping
    public R edit(VtlUser vtlUser) {
        boolean bool = vtlUserService.updateById(vtlUser);
        return R.bool(bool);
    }

    @DeleteMapping("/{id}")
    public R delete(@PathVariable("id") Long id) {
        boolean bool = vtlUserService.removeById(id);
        return R.bool(bool);
    }

    @DeleteMapping
    public R delete(Long[] ids) {
        boolean bool = vtlUserService.removeBatchByIds(Arrays.asList(ids));
        return R.bool(bool);
    }

    @GetMapping("/{id}")
    public VtlUser getById(@PathVariable("id") Long id) {
        return vtlUserService.getById(id);
    }

    @GetMapping("/page")
    public IPage<VtlUserDTO> page(Page<VtlUserDTO> page, VtlUserDTO dto) {
        return vtlUserService.page(page, dto);
    }
}

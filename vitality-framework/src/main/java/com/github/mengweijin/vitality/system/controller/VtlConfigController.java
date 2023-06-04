package com.github.mengweijin.vitality.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.mengweijin.vitality.framework.domain.R;
import com.github.mengweijin.vitality.framework.mvc.BaseController;
import com.github.mengweijin.vitality.system.dto.VtlConfigDTO;
import com.github.mengweijin.vitality.system.entity.VtlConfig;
import com.github.mengweijin.vitality.system.service.VtlConfigService;
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
 * 配置管理表 控制器
 *
 * @author mengweijin
 * @since 2023-06-04
 */
@RestController
@RequestMapping("/vtl-config")
public class VtlConfigController extends BaseController {

    @Autowired
    private VtlConfigService vtlConfigService;

    @PostMapping
    public R add(VtlConfig vtlConfig) {
        boolean bool = vtlConfigService.save(vtlConfig);
        return R.bool(bool);
    }

    @PutMapping
    public R edit(VtlConfig vtlConfig) {
        boolean bool = vtlConfigService.updateById(vtlConfig);
        return R.bool(bool);
    }

    @DeleteMapping("/{id}")
    public R delete(@PathVariable("id") Long id) {
        boolean bool = vtlConfigService.removeById(id);
        return R.bool(bool);
    }

    @DeleteMapping
    public R delete(Long[] ids) {
        boolean bool = vtlConfigService.removeBatchByIds(Arrays.asList(ids));
        return R.bool(bool);
    }

    @GetMapping("/{id}")
    public VtlConfig getById(@PathVariable("id") Long id) {
        return vtlConfigService.getById(id);
    }

    @GetMapping("/detail/{id}")
    public VtlConfigDTO detailById(@PathVariable("id") Long id) {
        return vtlConfigService.detailById(id);
    }

    @GetMapping("/page")
    public IPage<VtlConfigDTO> page(Page<VtlConfigDTO> page, VtlConfigDTO dto) {
        return vtlConfigService.page(page, dto);
    }
}

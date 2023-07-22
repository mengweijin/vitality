package com.github.mengweijin.vitality.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.mengweijin.vitality.framework.domain.R;
import com.github.mengweijin.vitality.framework.mvc.BaseController;
import com.github.mengweijin.vitality.system.dto.VtlDictDataDTO;
import com.github.mengweijin.vitality.system.entity.VtlDictData;
import com.github.mengweijin.vitality.system.service.VtlDictDataService;
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
 * 字典数据表 控制器
 *
 * @author mengweijin
 * @since 2023-06-02
 */
@RestController
@RequestMapping("/vtl-dict-data")
public class VtlDictDataController extends BaseController {

    @Autowired
    private VtlDictDataService vtlDictDataService;

    @SaCheckPermission("system:dict:data:add")
    @PostMapping
    public R add(VtlDictData vtlDictData) {
        boolean bool = vtlDictDataService.save(vtlDictData);
        return R.bool(bool);
    }

    @SaCheckPermission("system:dict:data:edit")
    @PutMapping
    public R edit(VtlDictData vtlDictData) {
        boolean bool = vtlDictDataService.updateById(vtlDictData);
        return R.bool(bool);
    }

    @SaCheckPermission("system:dict:data:delete")
    @DeleteMapping("/{id}")
    public R delete(@PathVariable("id") Long id) {
        boolean bool = vtlDictDataService.removeById(id);
        return R.bool(bool);
    }

    @SaCheckPermission("system:dict:data:delete")
    @DeleteMapping
    public R delete(Long[] ids) {
        boolean bool = vtlDictDataService.removeBatchByIds(Arrays.asList(ids));
        return R.bool(bool);
    }

    @GetMapping("/{id}")
    public VtlDictData getById(@PathVariable("id") Long id) {
        return vtlDictDataService.getById(id);
    }

    @SaCheckPermission("system:dict:data:list")
    @GetMapping("/page")
    public IPage<VtlDictDataDTO> page(Page<VtlDictDataDTO> page, VtlDictDataDTO dto) {
        return vtlDictDataService.page(page, dto);
    }

    @GetMapping("/list/all")
    public List<VtlDictData> listAll() {
        return vtlDictDataService.lambdaQuery().list();
    }

    @SaCheckPermission("system:dict:data:disabled")
    @PostMapping("/setDisabledValue/{id}")
    public R setDisabledValue(@PathVariable("id") Long id, boolean disabled) {
        boolean bool = vtlDictDataService.setDisabledValue(id, disabled);
        return R.bool(bool);
    }

    @SaCheckPermission("system:dict:data:setDefault")
    @PostMapping("/setDefaultSelected/{id}")
    public R setDefaultSelected(@PathVariable("id") Long id) {
        boolean bool = vtlDictDataService.setDefaultSelected(id);
        return R.bool(bool);
    }
}

package com.github.mengweijin.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.mengweijin.framework.domain.R;
import com.github.mengweijin.framework.mvc.BaseController;
import com.github.mengweijin.system.dto.DictDataDTO;
import com.github.mengweijin.system.entity.DictDataDO;
import com.github.mengweijin.system.service.DictDataService;
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
public class DictDataController extends BaseController {

    @Autowired
    private DictDataService dictDataService;

    @SaCheckPermission("system:dict:data:add")
    @PostMapping
    public R add(DictDataDO dictDataDO) {
        boolean bool = dictDataService.save(dictDataDO);
        return R.ajax(bool);
    }

    @SaCheckPermission("system:dict:data:edit")
    @PutMapping
    public R edit(DictDataDO dictDataDO) {
        boolean bool = dictDataService.updateById(dictDataDO);
        return R.ajax(bool);
    }

    @SaCheckPermission("system:dict:data:delete")
    @DeleteMapping("/{id}")
    public R delete(@PathVariable("id") Long id) {
        boolean bool = dictDataService.removeById(id);
        return R.ajax(bool);
    }

    @SaCheckPermission("system:dict:data:delete")
    @DeleteMapping
    public R delete(Long[] ids) {
        boolean bool = dictDataService.removeBatchByIds(Arrays.asList(ids));
        return R.ajax(bool);
    }

    @GetMapping("/{id}")
    public DictDataDO getById(@PathVariable("id") Long id) {
        return dictDataService.getById(id);
    }

    @SaCheckPermission("system:dict:data:list")
    @GetMapping("/page")
    public IPage<DictDataDTO> page(Page<DictDataDTO> page, DictDataDTO dto) {
        return dictDataService.page(page, dto);
    }

    @GetMapping("/list/all")
    public List<DictDataDO> listAll() {
        return dictDataService.lambdaQuery().list();
    }

    @SaCheckPermission("system:dict:data:disabled")
    @PostMapping("/setDisabledValue/{id}")
    public R setDisabledValue(@PathVariable("id") Long id, boolean disabled) {
        boolean bool = dictDataService.setDisabledValue(id, disabled);
        return R.ajax(bool);
    }

    @SaCheckPermission("system:dict:data:setDefault")
    @PostMapping("/setDefaultSelected/{id}")
    public R setDefaultSelected(@PathVariable("id") Long id) {
        boolean bool = dictDataService.setDefaultSelected(id);
        return R.ajax(bool);
    }
}

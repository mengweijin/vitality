package com.github.mengweijin.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.mengweijin.framework.domain.R;
import com.github.mengweijin.framework.mvc.BaseController;
import com.github.mengweijin.system.dto.DictTypeDTO;
import com.github.mengweijin.system.entity.DictTypeDO;
import com.github.mengweijin.system.service.DictTypeService;
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
 * 字典类型表 控制器
 *
 * @author mengweijin
 * @since 2023-06-02
 */
@RestController
@RequestMapping("/vtl-dict-type")
public class DictTypeController extends BaseController {

    @Autowired
    private DictTypeService dictTypeService;

    @SaCheckPermission("system:dict:type:add")
    @PostMapping
    public R add(DictTypeDO dictTypeDO) {
        boolean bool = dictTypeService.save(dictTypeDO);
        return R.ajax(bool);
    }

    @SaCheckPermission("system:dict:type:edit")
    @PutMapping
    public R edit(DictTypeDO dictTypeDO) {
        boolean bool = dictTypeService.updateById(dictTypeDO);
        return R.ajax(bool);
    }

    @SaCheckPermission("system:dict:type:delete")
    @DeleteMapping("/{id}")
    public R delete(@PathVariable("id") Long id) {
        boolean bool = dictTypeService.removeById(id);
        return R.ajax(bool);
    }

    @SaCheckPermission("system:dict:type:delete")
    @DeleteMapping
    public R delete(Long[] ids) {
        boolean bool = dictTypeService.removeBatchByIds(Arrays.asList(ids));
        return R.ajax(bool);
    }

    @GetMapping("/{id}")
    public DictTypeDO getById(@PathVariable("id") Long id) {
        return dictTypeService.getById(id);
    }

    @SaCheckPermission("system:dict:type:list")
    @GetMapping("/page")
    public IPage<DictTypeDTO> page(Page<DictTypeDTO> page, DictTypeDTO dto) {
        return dictTypeService.page(page, dto);
    }
}

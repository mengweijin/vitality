package com.github.mengweijin.vitality.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.mengweijin.vitality.framework.domain.R;
import com.github.mengweijin.vitality.framework.mvc.BaseController;
import com.github.mengweijin.vitality.system.dto.VtlDictTypeDTO;
import com.github.mengweijin.vitality.system.entity.VtlDictType;
import com.github.mengweijin.vitality.system.service.VtlDictTypeService;
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
public class VtlDictTypeController extends BaseController {

    @Autowired
    private VtlDictTypeService vtlDictTypeService;

    @PostMapping
    public R add(VtlDictType vtlDictType) {
        boolean bool = vtlDictTypeService.save(vtlDictType);
        return R.bool(bool);
    }

    @PutMapping
    public R edit(VtlDictType vtlDictType) {
        boolean bool = vtlDictTypeService.updateById(vtlDictType);
        return R.bool(bool);
    }

    @DeleteMapping("/{id}")
    public R delete(@PathVariable("id") Long id) {
        boolean bool = vtlDictTypeService.removeById(id);
        return R.bool(bool);
    }

    @DeleteMapping
    public R delete(Long[] ids) {
        boolean bool = vtlDictTypeService.removeBatchByIds(Arrays.asList(ids));
        return R.bool(bool);
    }

    @GetMapping("/{id}")
    public VtlDictType getById(@PathVariable("id") Long id) {
        return vtlDictTypeService.getById(id);
    }

    @GetMapping("/page")
    public IPage<VtlDictTypeDTO> page(Page<VtlDictTypeDTO> page, VtlDictTypeDTO dto) {
        return vtlDictTypeService.page(page, dto);
    }
}

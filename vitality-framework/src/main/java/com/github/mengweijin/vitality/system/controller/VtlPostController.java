package com.github.mengweijin.vitality.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.mengweijin.vitality.framework.domain.R;
import com.github.mengweijin.vitality.framework.mvc.BaseController;
import com.github.mengweijin.vitality.system.dto.VtlPostDTO;
import com.github.mengweijin.vitality.system.entity.VtlPost;
import com.github.mengweijin.vitality.system.service.VtlPostService;
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
 * 岗位管理表 控制器
 *
 * @author mengweijin
 * @since 2023-06-09
 */
@RestController
@RequestMapping("/vtl-post")
public class VtlPostController extends BaseController {

    @Autowired
    private VtlPostService vtlPostService;

    @PostMapping
    public R add(VtlPost vtlPost) {
        boolean bool = vtlPostService.save(vtlPost);
        return R.bool(bool);
    }

    @PutMapping
    public R edit(VtlPost vtlPost) {
        boolean bool = vtlPostService.updateById(vtlPost);
        return R.bool(bool);
    }

    @DeleteMapping("/{id}")
    public R delete(@PathVariable("id") Long id) {
        boolean bool = vtlPostService.removeById(id);
        return R.bool(bool);
    }

    @DeleteMapping
    public R delete(Long[] ids) {
        boolean bool = vtlPostService.removeBatchByIds(Arrays.asList(ids));
        return R.bool(bool);
    }

    @GetMapping("/{id}")
    public VtlPost getById(@PathVariable("id") Long id) {
        return vtlPostService.getById(id);
    }

    @GetMapping("/detail/{id}")
    public VtlPostDTO detailById(@PathVariable("id") Long id) {
        return vtlPostService.detailById(id);
    }

    @GetMapping("/page")
    public IPage<VtlPostDTO> page(Page<VtlPostDTO> page, VtlPostDTO dto) {
        return vtlPostService.page(page, dto);
    }

    @PostMapping("/setDisabledValue/{id}")
    public R setDisabledValue(@PathVariable("id") Long id, boolean disabled) {
        boolean bool = vtlPostService.setDisabledValue(id, disabled);
        return R.bool(bool);
    }
}

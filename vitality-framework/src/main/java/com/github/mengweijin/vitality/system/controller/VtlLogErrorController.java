package com.github.mengweijin.vitality.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.mengweijin.vitality.framework.domain.R;
import com.github.mengweijin.vitality.framework.mvc.BaseController;
import com.github.mengweijin.vitality.system.dto.VtlLogErrorDTO;
import com.github.mengweijin.vitality.system.entity.VtlLogError;
import com.github.mengweijin.vitality.system.service.VtlLogErrorService;
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
 * 系统错误日志记录表 控制器
 *
 * @author mengweijin
 * @since 2023-06-06
 */
@RestController
@RequestMapping("/vtl-log-error")
public class VtlLogErrorController extends BaseController {

    @Autowired
    private VtlLogErrorService vtlLogErrorService;

    @PostMapping
    public R add(VtlLogError vtlLogError) {
        boolean bool = vtlLogErrorService.save(vtlLogError);
        return R.bool(bool);
    }

    @PutMapping
    public R edit(VtlLogError vtlLogError) {
        boolean bool = vtlLogErrorService.updateById(vtlLogError);
        return R.bool(bool);
    }

    @DeleteMapping("/{id}")
    public R delete(@PathVariable("id") Long id) {
        boolean bool = vtlLogErrorService.removeById(id);
        return R.bool(bool);
    }

    @DeleteMapping
    public R delete(Long[] ids) {
        boolean bool = vtlLogErrorService.removeBatchByIds(Arrays.asList(ids));
        return R.bool(bool);
    }

    @GetMapping("/{id}")
    public VtlLogError getById(@PathVariable("id") Long id) {
        return vtlLogErrorService.getById(id);
    }

    @GetMapping("/detail/{id}")
    public VtlLogErrorDTO detailById(@PathVariable("id") Long id) {
        return vtlLogErrorService.detailById(id);
    }

    @GetMapping("/page")
    public IPage<VtlLogErrorDTO> page(Page<VtlLogErrorDTO> page, VtlLogErrorDTO dto) {
        return vtlLogErrorService.page(page, dto);
    }
}

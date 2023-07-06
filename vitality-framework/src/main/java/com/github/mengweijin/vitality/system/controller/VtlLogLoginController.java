package com.github.mengweijin.vitality.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.mengweijin.vitality.framework.domain.R;
import com.github.mengweijin.vitality.framework.mvc.BaseController;
import com.github.mengweijin.vitality.system.dto.VtlLogLoginDTO;
import com.github.mengweijin.vitality.system.entity.VtlLogLogin;
import com.github.mengweijin.vitality.system.service.VtlLogLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

/**
 * 登录日志记录表 控制器
 *
 * @author mengweijin
 * @since 2023-07-06
 */
@RestController
@RequestMapping("/vtl-log-login")
public class VtlLogLoginController extends BaseController {

    @Autowired
    private VtlLogLoginService vtlLogLoginService;

    @PostMapping
    public R add(VtlLogLogin vtlLogLogin) {
        boolean bool = vtlLogLoginService.save(vtlLogLogin);
        return R.bool(bool);
    }

    @PutMapping
    public R edit(VtlLogLogin vtlLogLogin) {
        boolean bool = vtlLogLoginService.updateById(vtlLogLogin);
        return R.bool(bool);
    }

    @DeleteMapping("/{id}")
    public R delete(@PathVariable("id") Long id) {
        boolean bool = vtlLogLoginService.removeById(id);
        return R.bool(bool);
    }

    @DeleteMapping
    public R delete(@RequestParam(value = "ids[]") Long[] ids) {
        boolean bool = vtlLogLoginService.removeBatchByIds(Arrays.asList(ids));
        return R.bool(bool);
    }

    @GetMapping("/{id}")
    public VtlLogLogin getById(@PathVariable("id") Long id) {
        return vtlLogLoginService.getById(id);
    }

    @GetMapping("/detail/{id}")
    public VtlLogLoginDTO detailById(@PathVariable("id") Long id) {
        return vtlLogLoginService.detailById(id);
    }

    @GetMapping("/page")
    public IPage<VtlLogLoginDTO> page(Page<VtlLogLoginDTO> page, VtlLogLoginDTO dto) {
        return vtlLogLoginService.page(page, dto);
    }
}

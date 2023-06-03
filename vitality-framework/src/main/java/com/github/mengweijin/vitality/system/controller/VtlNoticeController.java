package com.github.mengweijin.vitality.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.mengweijin.vitality.framework.domain.R;
import com.github.mengweijin.vitality.framework.mvc.BaseController;
import com.github.mengweijin.vitality.system.dto.VtlMessageHeaderMenuDataDTO;
import com.github.mengweijin.vitality.system.dto.VtlNoticeDTO;
import com.github.mengweijin.vitality.system.entity.VtlNotice;
import com.github.mengweijin.vitality.system.service.VtlNoticeService;
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
 * 通知记录表 控制器
 *
 * @author mengweijin
 * @since 2023-06-03
 */
@RestController
@RequestMapping("/vtl-notice")
public class VtlNoticeController extends BaseController {

    @Autowired
    private VtlNoticeService vtlNoticeService;

    @PostMapping
    public R add(VtlNotice vtlNotice) {
        boolean bool = vtlNoticeService.save(vtlNotice);
        return R.bool(bool);
    }

    @PutMapping
    public R edit(VtlNotice vtlNotice) {
        boolean bool = vtlNoticeService.updateById(vtlNotice);
        return R.bool(bool);
    }

    @DeleteMapping("/{id}")
    public R delete(@PathVariable("id") Long id) {
        boolean bool = vtlNoticeService.removeById(id);
        return R.bool(bool);
    }

    @DeleteMapping
    public R delete(Long[] ids) {
        boolean bool = vtlNoticeService.removeBatchByIds(Arrays.asList(ids));
        return R.bool(bool);
    }

    @GetMapping("/{id}")
    public VtlNotice getById(@PathVariable("id") Long id) {
        return vtlNoticeService.getById(id);
    }

    @GetMapping("/detail/{id}")
    public VtlNoticeDTO detailById(@PathVariable("id") Long id) {
        return vtlNoticeService.detailById(id);
    }

    @GetMapping("/page")
    public IPage<VtlNoticeDTO> page(Page<VtlNoticeDTO> page, VtlNoticeDTO dto) {
        return vtlNoticeService.page(page, dto);
    }

    @GetMapping("/headerMenuData")
    public List<VtlMessageHeaderMenuDataDTO> headerMenuData() {
        return vtlNoticeService.headerMenuData();
    }

    @PostMapping("/release/{id}")
    public R release(@PathVariable("id") Long id) {
        boolean bool = vtlNoticeService.lambdaUpdate().set(VtlNotice::getReleased, 1).eq(VtlNotice::getId, id).update();
        return R.bool(bool);
    }

    @PostMapping("/revocation/{id}")
    public R revocation(@PathVariable("id") Long id) {
        boolean bool = vtlNoticeService.lambdaUpdate().set(VtlNotice::getReleased, 0).eq(VtlNotice::getId, id).update();
        return R.bool(bool);
    }
}

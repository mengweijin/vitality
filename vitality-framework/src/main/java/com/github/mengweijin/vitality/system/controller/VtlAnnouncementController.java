package com.github.mengweijin.vitality.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.mengweijin.vitality.framework.domain.R;
import com.github.mengweijin.vitality.framework.mvc.BaseController;
import com.github.mengweijin.vitality.system.dto.VtlAnnouncementDTO;
import com.github.mengweijin.vitality.system.entity.VtlAnnouncement;
import com.github.mengweijin.vitality.system.service.VtlAnnouncementService;
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
 * 公告管理表 控制器
 *
 * @author mengweijin
 * @since 2023-06-03
 */
@RestController
@RequestMapping("/vtl-announcement")
public class VtlAnnouncementController extends BaseController {

    @Autowired
    private VtlAnnouncementService vtlAnnouncementService;

    @PostMapping
    public R add(VtlAnnouncement vtlAnnouncement) {
        boolean bool = vtlAnnouncementService.save(vtlAnnouncement);
        return R.bool(bool);
    }

    @PutMapping
    public R edit(VtlAnnouncement vtlAnnouncement) {
        boolean bool = vtlAnnouncementService.updateById(vtlAnnouncement);
        return R.bool(bool);
    }

    @DeleteMapping("/{id}")
    public R delete(@PathVariable("id") Long id) {
        boolean bool = vtlAnnouncementService.removeById(id);
        return R.bool(bool);
    }

    @DeleteMapping
    public R delete(Long[] ids) {
        boolean bool = vtlAnnouncementService.removeBatchByIds(Arrays.asList(ids));
        return R.bool(bool);
    }

    @GetMapping("/{id}")
    public VtlAnnouncement getById(@PathVariable("id") Long id) {
        return vtlAnnouncementService.getById(id);
    }

    @GetMapping("/detail/{id}")
    public VtlAnnouncementDTO detailById(@PathVariable("id") Long id) {
        return vtlAnnouncementService.detailById(id);
    }

    @GetMapping("/page")
    public IPage<VtlAnnouncementDTO> page(Page<VtlAnnouncementDTO> page, VtlAnnouncementDTO dto) {
        return vtlAnnouncementService.page(page, dto);
    }
}

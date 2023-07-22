package com.github.mengweijin.vitality.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
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

    @SaCheckPermission("system:announcement:add")
    @PostMapping
    public R add(VtlAnnouncement vtlAnnouncement) {
        boolean bool = vtlAnnouncementService.save(vtlAnnouncement);
        return R.bool(bool);
    }

    @SaCheckPermission("system:announcement:edit")
    @PutMapping
    public R edit(VtlAnnouncement vtlAnnouncement) {
        boolean bool = vtlAnnouncementService.updateById(vtlAnnouncement);
        return R.bool(bool);
    }

    @SaCheckPermission("system:announcement:delete")
    @DeleteMapping("/{id}")
    public R delete(@PathVariable("id") Long id) {
        boolean bool = vtlAnnouncementService.removeById(id);
        return R.bool(bool);
    }

    @SaCheckPermission("system:announcement:delete")
    @DeleteMapping
    public R delete(Long[] ids) {
        boolean bool = vtlAnnouncementService.removeBatchByIds(Arrays.asList(ids));
        return R.bool(bool);
    }

    @GetMapping("/{id}")
    public VtlAnnouncement getById(@PathVariable("id") Long id) {
        return vtlAnnouncementService.getById(id);
    }

    @SaCheckPermission("system:announcement:detail")
    @GetMapping("/detail/{id}")
    public VtlAnnouncementDTO detailById(@PathVariable("id") Long id) {
        return vtlAnnouncementService.detailById(id);
    }

    @SaCheckPermission("system:announcement:list")
    @GetMapping("/page")
    public IPage<VtlAnnouncementDTO> page(Page<VtlAnnouncementDTO> page, VtlAnnouncementDTO dto) {
        return vtlAnnouncementService.page(page, dto);
    }

    @SaCheckPermission("system:announcement:release")
    @PostMapping("/release/{id}")
    public R release(@PathVariable("id") Long id) {
        boolean bool = vtlAnnouncementService.lambdaUpdate().set(VtlAnnouncement::getReleased, 1).eq(VtlAnnouncement::getId, id).update();
        return R.bool(bool);
    }

    @SaCheckPermission("system:announcement:revocation")
    @PostMapping("/revocation/{id}")
    public R revocation(@PathVariable("id") Long id) {
        boolean bool = vtlAnnouncementService.lambdaUpdate().set(VtlAnnouncement::getReleased, 0).eq(VtlAnnouncement::getId, id).update();
        return R.bool(bool);
    }
}

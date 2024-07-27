package com.github.mengweijin.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.mengweijin.framework.domain.R;
import com.github.mengweijin.framework.mvc.BaseController;
import com.github.mengweijin.system.dto.AnnouncementDTO;
import com.github.mengweijin.system.entity.AnnouncementDO;
import com.github.mengweijin.system.service.AnnouncementService;
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
public class AnnouncementController extends BaseController {

    @Autowired
    private AnnouncementService announcementService;

    @SaCheckPermission("system:announcement:add")
    @PostMapping
    public R add(AnnouncementDO announcementDO) {
        boolean bool = announcementService.save(announcementDO);
        return R.ajax(bool);
    }

    @SaCheckPermission("system:announcement:edit")
    @PutMapping
    public R edit(AnnouncementDO announcementDO) {
        boolean bool = announcementService.updateById(announcementDO);
        return R.ajax(bool);
    }

    @SaCheckPermission("system:announcement:delete")
    @DeleteMapping("/{id}")
    public R delete(@PathVariable("id") Long id) {
        boolean bool = announcementService.removeById(id);
        return R.ajax(bool);
    }

    @SaCheckPermission("system:announcement:delete")
    @DeleteMapping
    public R delete(Long[] ids) {
        boolean bool = announcementService.removeBatchByIds(Arrays.asList(ids));
        return R.ajax(bool);
    }

    @GetMapping("/{id}")
    public AnnouncementDO getById(@PathVariable("id") Long id) {
        return announcementService.getById(id);
    }

    @SaCheckPermission("system:announcement:detail")
    @GetMapping("/detail/{id}")
    public AnnouncementDTO detailById(@PathVariable("id") Long id) {
        return announcementService.detailById(id);
    }

    @SaCheckPermission("system:announcement:list")
    @GetMapping("/page")
    public IPage<AnnouncementDTO> page(Page<AnnouncementDTO> page, AnnouncementDTO dto) {
        return announcementService.page(page, dto);
    }

    @SaCheckPermission("system:announcement:release")
    @PostMapping("/release/{id}")
    public R release(@PathVariable("id") Long id) {
        boolean bool = announcementService.lambdaUpdate().set(AnnouncementDO::getReleased, 1).eq(AnnouncementDO::getId, id).update();
        return R.ajax(bool);
    }

    @SaCheckPermission("system:announcement:revocation")
    @PostMapping("/revocation/{id}")
    public R revocation(@PathVariable("id") Long id) {
        boolean bool = announcementService.lambdaUpdate().set(AnnouncementDO::getReleased, 0).eq(AnnouncementDO::getId, id).update();
        return R.ajax(bool);
    }
}

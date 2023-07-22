package com.github.mengweijin.vitality.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.mengweijin.vitality.framework.domain.R;
import com.github.mengweijin.vitality.framework.mvc.BaseController;
import com.github.mengweijin.vitality.system.dto.MessageMenuHeaderDataDTO;
import com.github.mengweijin.vitality.system.dto.NoticeDTO;
import com.github.mengweijin.vitality.system.entity.NoticeDO;
import com.github.mengweijin.vitality.system.service.NoticeService;
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
public class NoticeController extends BaseController {

    @Autowired
    private NoticeService noticeService;

    @PostMapping
    public R add(NoticeDO noticeDO) {
        boolean bool = noticeService.save(noticeDO);
        return R.bool(bool);
    }

    @PutMapping
    public R edit(NoticeDO noticeDO) {
        boolean bool = noticeService.updateById(noticeDO);
        return R.bool(bool);
    }

    @DeleteMapping("/{id}")
    public R delete(@PathVariable("id") Long id) {
        boolean bool = noticeService.removeById(id);
        return R.bool(bool);
    }

    @DeleteMapping
    public R delete(Long[] ids) {
        boolean bool = noticeService.removeBatchByIds(Arrays.asList(ids));
        return R.bool(bool);
    }

    @GetMapping("/{id}")
    public NoticeDO getById(@PathVariable("id") Long id) {
        return noticeService.getById(id);
    }

    @GetMapping("/detail/{id}")
    public NoticeDTO detailById(@PathVariable("id") Long id) {
        return noticeService.detailById(id);
    }

    @GetMapping("/page")
    public IPage<NoticeDTO> page(Page<NoticeDTO> page, NoticeDTO dto) {
        return noticeService.page(page, dto);
    }

    @GetMapping("/headerMenuData")
    public List<MessageMenuHeaderDataDTO> headerMenuData() {
        return noticeService.headerMenuData();
    }

    @PostMapping("/release/{id}")
    public R release(@PathVariable("id") Long id) {
        boolean bool = noticeService.lambdaUpdate().set(NoticeDO::getReleased, 1).eq(NoticeDO::getId, id).update();
        return R.bool(bool);
    }

    @PostMapping("/revocation/{id}")
    public R revocation(@PathVariable("id") Long id) {
        boolean bool = noticeService.lambdaUpdate().set(NoticeDO::getReleased, 0).eq(NoticeDO::getId, id).update();
        return R.bool(bool);
    }
}

package com.github.mengweijin.vitality.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.mengweijin.vitality.framework.domain.R;
import com.github.mengweijin.vitality.framework.log.aspect.annotation.Log;
import com.github.mengweijin.vitality.framework.log.aspect.enums.EOperationType;
import com.github.mengweijin.vitality.system.domain.entity.Notice;
import com.github.mengweijin.vitality.system.enums.EYesNo;
import com.github.mengweijin.vitality.system.service.NoticeService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

/**
 * <p>
 *  Notice Controller
 * </p>
 *
 * @author mengweijin
 * @since 2023-06-03
 */
@Slf4j
@AllArgsConstructor
@Validated
@RestController
@RequestMapping("/system/notice")
public class NoticeController {

    private NoticeService noticeService;

    /**
     * <p>
     * Get Notice page by Notice
     * </p>
     * @param page page
     * @param notice {@link Notice}
     * @return Page<Notice>
     */
    @SaCheckPermission("system:notice:query")
    @GetMapping("/page")
    public IPage<Notice> page(Page<Notice> page, Notice notice) {
        return noticeService.page(page, notice);
    }

    /**
     * <p>
     * Get Notice list by Notice
     * </p>
     * @param notice {@link Notice}
     * @return List<Notice>
     */
    @SaCheckPermission("system:notice:query")
    @GetMapping("/list")
    public List<Notice> list(Notice notice) {
        return noticeService.list(new LambdaQueryWrapper<>(notice));
    }

    /**
     * <p>
     * Get Notice by id
     * </p>
     * @param id id
     * @return Notice
     */
    @SaCheckPermission("system:notice:query")
    @GetMapping("/{id}")
    public Notice getById(@PathVariable("id") Long id) {
        return noticeService.getById(id);
    }

    /**
     * <p>
     * Add Notice
     * </p>
     * @param notice {@link Notice}
     */
    @Log(operationType = EOperationType.INSERT)
    @SaCheckPermission("system:notice:create")
    @PostMapping("/create")
    public R<Void> create(@Valid @RequestBody Notice notice) {
        boolean bool = noticeService.save(notice);
        return R.ajax(bool);
    }

    /**
     * <p>
     * Update Notice
     * </p>
     * @param notice {@link Notice}
     */
    @Log(operationType = EOperationType.UPDATE)
    @SaCheckPermission("system:notice:update")
    @PostMapping("/update")
    public R<Void> update(@Valid @RequestBody Notice notice) {
        boolean bool = noticeService.updateById(notice);
        return R.ajax(bool);
    }

    /**
     * <p>
     * Delete Notice by id(s), Multiple ids can be separated by commas ",".
     * </p>
     * @param ids id
     */
    @Log(operationType = EOperationType.DELETE)
    @SaCheckPermission("system:notice:delete")
    @PostMapping("/delete/{ids}")
    public R<Void> delete(@PathVariable("ids") Long[] ids) {
        return R.ajax(noticeService.removeByIds(Arrays.asList(ids)));
    }

    @Log(operationType = EOperationType.UPDATE)
    @SaCheckPermission("system:notice:release")
    @PostMapping("/release/{id}")
    public R<Void> release(@PathVariable("id") Long id) {
        boolean bool = noticeService.lambdaUpdate().set(Notice::getReleased, EYesNo.Y.getValue()).eq(Notice::getId, id).update();
        return R.ajax(bool);
    }

    @Log(operationType = EOperationType.UPDATE)
    @SaCheckPermission("system:notice:revocation")
    @PostMapping("/revoke/{id}")
    public R<Void> revoke(@PathVariable("id") Long id) {
        boolean bool = noticeService.lambdaUpdate().set(Notice::getReleased, EYesNo.N.getValue()).eq(Notice::getId, id).update();
        return R.ajax(bool);
    }
}


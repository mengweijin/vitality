package com.github.mengweijin.vitality.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.mengweijin.vitality.framework.domain.R;
import com.github.mengweijin.vitality.framework.log.aspect.annotation.Log;
import com.github.mengweijin.vitality.framework.log.aspect.enums.EOperationType;
import com.github.mengweijin.vitality.framework.util.DownLoadUtils;
import com.github.mengweijin.vitality.framework.validator.group.Group;
import com.github.mengweijin.vitality.system.domain.entity.Oss;
import com.github.mengweijin.vitality.system.service.OssService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dromara.hutool.core.io.file.FileUtil;
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
 *  File Controller
 * </p>
 *
 * @author mengweijin
 * @since 2023-06-03
 */
@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/system/oss")
public class OssController {

    private OssService ossService;

    @PostMapping("/upload")
    public List<Oss> upload(HttpServletRequest request) {
        return ossService.upload(request);
    }

    /**
     * @param id id in table VTL_OSS
     */
    @GetMapping("/download/{id}")
    public R<Void> download(@PathVariable("id") Long id, HttpServletRequest request, HttpServletResponse response) {
        Oss oss = ossService.getById(id);
        if(oss == null) {
            return R.failure("No file was found!");
        }
        DownLoadUtils.download(FileUtil.getInputStream(oss.getStoragePath()), oss.getName(), request, response);
        return R.success();
    }

    /**
     * <p>
     * Get File page by File
     * </p>
     * @param page page
     * @param oss {@link Oss}
     * @return Page<File>
     */
    @SaCheckPermission("system:oss:query")
    @GetMapping("/page")
    public IPage<Oss> page(Page<Oss> page, Oss oss) {
        return ossService.page(page, oss);
    }

    /**
     * <p>
     * Get File list by File
     * </p>
     * @param oss {@link Oss}
     * @return List<File>
     */
    @SaCheckPermission("system:oss:query")
    @GetMapping("/list")
    public List<Oss> list(Oss oss) {
        return ossService.list(new LambdaQueryWrapper<>(oss));
    }

    /**
     * <p>
     * Get File by id
     * </p>
     * @param id id
     * @return File
     */
    @SaCheckPermission("system:oss:query")
    @GetMapping("/{id}")
    public Oss getById(@PathVariable("id") Long id) {
        return ossService.getById(id);
    }

    /**
     * <p>
     * Add File
     * </p>
     * @param oss {@link Oss}
     */
    @SaCheckPermission("system:oss:create")
    @PostMapping("/create")
    public R<Void> create(@Validated({Group.Default.class, Group.Create.class}) @RequestBody Oss oss) {
        boolean bool = ossService.save(oss);
        return R.ajax(bool);
    }

    /**
     * <p>
     * Update File
     * </p>
     * @param oss {@link Oss}
     */
    @SaCheckPermission("system:oss:update")
    @PostMapping("/update")
    public R<Void> update(@Validated({Group.Default.class, Group.Update.class}) @RequestBody Oss oss) {
        boolean bool = ossService.updateById(oss);
        return R.ajax(bool);
    }

    /**
     * <p>
     * Delete File by id(s), Multiple ids can be separated by commas ",".
     * </p>
     * @param ids id
     */
    @Log(operationType = EOperationType.DELETE)
    @SaCheckPermission("system:oss:delete")
    @PostMapping("/delete/{ids}")
    public R<Void> delete(@PathVariable("ids") Long[] ids) {
        return R.ajax(ossService.removeByIds(Arrays.asList(ids)));
    }

}


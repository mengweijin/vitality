package com.github.mengweijin.vitality.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.mengweijin.vitality.framework.domain.R;
import com.github.mengweijin.vitality.framework.mvc.BaseController;
import com.github.mengweijin.vitality.system.dto.ConfigDTO;
import com.github.mengweijin.vitality.system.entity.ConfigDO;
import com.github.mengweijin.vitality.system.service.ConfigService;
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
 * 配置管理表 控制器
 *
 * @author mengweijin
 * @since 2023-06-04
 */
@RestController
@RequestMapping("/vtl-config")
public class ConfigController extends BaseController {

    @Autowired
    private ConfigService configService;

    @SaCheckPermission("system:config:add")
    @PostMapping
    public R add(ConfigDO configDO) {
        boolean bool = configService.save(configDO);
        return R.bool(bool);
    }

    @SaCheckPermission("system:config:edit")
    @PutMapping
    public R edit(ConfigDO configDO) {
        boolean bool = configService.updateById(configDO);
        return R.bool(bool);
    }

    @SaCheckPermission("system:config:delete")
    @DeleteMapping("/{id}")
    public R delete(@PathVariable("id") Long id) {
        boolean bool = configService.removeById(id);
        return R.bool(bool);
    }

    @SaCheckPermission("system:config:delete")
    @DeleteMapping
    public R delete(Long[] ids) {
        boolean bool = configService.removeBatchByIds(Arrays.asList(ids));
        return R.bool(bool);
    }

    @GetMapping("/{id}")
    public ConfigDO getById(@PathVariable("id") Long id) {
        return configService.getById(id);
    }

    @SaCheckPermission("system:config:detail")
    @GetMapping("/detail/{id}")
    public ConfigDTO detailById(@PathVariable("id") Long id) {
        return configService.detailById(id);
    }

    @SaCheckPermission("system:config:list")
    @GetMapping("/page")
    public IPage<ConfigDTO> page(Page<ConfigDTO> page, ConfigDTO dto) {
        return configService.page(page, dto);
    }

    @GetMapping("/byCode/{code}")
    public ConfigDO byCode(@PathVariable("code") String code) {
        return configService.getByCode(code);
    }

}

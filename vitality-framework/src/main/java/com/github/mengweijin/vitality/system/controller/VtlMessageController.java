package com.github.mengweijin.vitality.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.mengweijin.vitality.framework.domain.R;
import com.github.mengweijin.vitality.system.dto.VtlMessageDTO;
import com.github.mengweijin.vitality.system.dto.VtlMessageHeaderMenuDataDTO;
import com.github.mengweijin.vitality.system.entity.VtlMessage;
import com.github.mengweijin.vitality.system.service.VtlMessageService;
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
 * @author mengweijin
 * @date 2023/4/1
 */
@RestController
@RequestMapping("/vtl-message")
public class VtlMessageController {
    @Autowired
    private VtlMessageService messageService;

    @PostMapping
    public R add(VtlMessage vtlMessage) {
        boolean bool = messageService.save(vtlMessage);
        return R.bool(bool);
    }

    @PutMapping
    public R edit(VtlMessage vtlMessage) {
        boolean bool = messageService.updateById(vtlMessage);
        return R.bool(bool);
    }

    @DeleteMapping("/{id}")
    public R delete(@PathVariable("id") Long id) {
        boolean bool = messageService.removeById(id);
        return R.bool(bool);
    }

    @DeleteMapping
    public R delete(Long[] ids) {
        boolean bool = messageService.removeBatchByIds(Arrays.asList(ids));
        return R.bool(bool);
    }

    @GetMapping("/{id}")
    public VtlMessage getById(@PathVariable("id") Long id) {
        return messageService.getById(id);
    }

    @GetMapping("/page")
    public IPage<VtlMessageDTO> page(Page<VtlMessageDTO> page, VtlMessageDTO dto) {
        return messageService.page(page, dto);
    }

    @GetMapping("/detail/{id}")
    public VtlMessageDTO detail(@PathVariable("id") Long id) {
        return messageService.detail(id);
    }


    @GetMapping("/headerMenuData")
    public List<VtlMessageHeaderMenuDataDTO> headerMenuData() {
        return messageService.headerMenuData();
    }
}

package com.github.mengweijin.vitality.demo.sys.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.mengweijin.vitality.controller.BaseController;
import com.github.mengweijin.vitality.demo.sys.dto.SysGoodsDTO;
import com.github.mengweijin.vitality.demo.sys.entity.SysGoods;
import com.github.mengweijin.vitality.demo.sys.service.SysGoodsService;
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
 * 商品表 控制器
 *
 * @author mengweijin
 * @since 2022-12-09
 */
@RestController
@RequestMapping("/sys-goods")
public class SysGoodsController extends BaseController {

    @Autowired
    private SysGoodsService sysGoodsService;

    @PostMapping
    public void add(SysGoods sysGoods) {
        sysGoodsService.save(sysGoods);
    }

    @PutMapping
    public void edit(SysGoods sysGoods) {
        sysGoodsService.updateById(sysGoods);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        sysGoodsService.removeById(id);
    }

    @DeleteMapping
    public void delete(Long[] ids) {
        sysGoodsService.removeBatchByIds(Arrays.asList(ids));
    }

    @GetMapping("/{id}")
    public SysGoods getById(@PathVariable("id") Long id) {
        return sysGoodsService.getById(id);
    }

    @GetMapping("/page")
    public IPage<SysGoodsDTO> page(Page<SysGoodsDTO> page, SysGoodsDTO dto) {
        return sysGoodsService.page(page, dto);
    }
}

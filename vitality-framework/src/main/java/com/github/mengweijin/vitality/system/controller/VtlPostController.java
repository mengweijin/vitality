package com.github.mengweijin.vitality.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.mengweijin.vitality.framework.domain.R;
import com.github.mengweijin.vitality.framework.mvc.BaseController;
import com.github.mengweijin.vitality.system.dto.VtlPostDTO;
import com.github.mengweijin.vitality.system.entity.VtlPost;
import com.github.mengweijin.vitality.system.service.VtlPostService;
import org.dromara.hutool.core.collection.ListUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

/**
 * 岗位管理表 控制器
 *
 * @author mengweijin
 * @since 2023-06-09
 */
@RestController
@RequestMapping("/vtl-post")
public class VtlPostController extends BaseController {

    @Autowired
    private VtlPostService vtlPostService;

    @SaCheckPermission("system:post:add")
    @PostMapping
    public R add(VtlPost vtlPost) {
        boolean bool = vtlPostService.save(vtlPost);
        return R.bool(bool);
    }

    @SaCheckPermission("system:post:edit")
    @PutMapping
    public R edit(VtlPost vtlPost) {
        boolean bool = vtlPostService.updateById(vtlPost);
        return R.bool(bool);
    }

    @SaCheckPermission("system:post:delete")
    @DeleteMapping("/{id}")
    public R delete(@PathVariable("id") Long id) {
        boolean bool = vtlPostService.removeById(id);
        return R.bool(bool);
    }

    @GetMapping("/{id}")
    public VtlPost getById(@PathVariable("id") Long id) {
        return vtlPostService.getById(id);
    }

    @SaCheckPermission("system:post:detail")
    @GetMapping("/detail/{id}")
    public VtlPostDTO detailById(@PathVariable("id") Long id) {
        return vtlPostService.detailById(id);
    }

    @SaCheckPermission("system:post:list")
    @GetMapping("/page")
    public IPage<VtlPostDTO> page(Page<VtlPostDTO> page, VtlPostDTO dto) {
        return vtlPostService.page(page, dto);
    }

    @GetMapping("/list")
    public List<VtlPost> list() {
        return vtlPostService.lambdaQuery().eq(VtlPost::getDisabled, 0).list();
    }

    @SaCheckPermission("system:post:disabled")
    @PostMapping("/setDisabledValue/{id}")
    public R setDisabledValue(@PathVariable("id") Long id, boolean disabled) {
        boolean bool = vtlPostService.setDisabledValue(id, disabled);
        return R.bool(bool);
    }

    @SaCheckPermission("system:post:assignUser")
    @PostMapping("/addUser/{id}")
    public R addUsers(@PathVariable("id") Long id, @RequestParam(value = "userIdList[]") Long[] userIdList) {
        vtlPostService.addUsers(id, ListUtil.of(userIdList));
        return R.success();
    }

    @DeleteMapping("/removeUser/{id}")
    public R removeUsers(@PathVariable("id") Long id, @RequestParam(value = "userIdList[]") Long[] userIdList) {
        vtlPostService.removeUsers(id, Arrays.asList(userIdList));
        return R.success();
    }

    @SaCheckPermission("system:post:authorization")
    @PostMapping("/setMenu/{id}")
    public R setMenu(@PathVariable("id") Long id, @RequestParam(value = "menuIdList[]", required = false) Long[] menuIdList) {
        vtlPostService.setMenu(id, ListUtil.of(menuIdList));
        return R.success();
    }

}

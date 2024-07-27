package com.github.mengweijin.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.mengweijin.framework.domain.R;
import com.github.mengweijin.framework.mvc.BaseController;
import com.github.mengweijin.system.dto.PostDTO;
import com.github.mengweijin.system.entity.PostDO;
import com.github.mengweijin.system.service.PostService;
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
public class PostController extends BaseController {

    @Autowired
    private PostService postService;

    @SaCheckPermission("system:post:add")
    @PostMapping
    public R add(PostDO postDO) {
        boolean bool = postService.save(postDO);
        return R.ajax(bool);
    }

    @SaCheckPermission("system:post:edit")
    @PutMapping
    public R edit(PostDO postDO) {
        boolean bool = postService.updateById(postDO);
        return R.ajax(bool);
    }

    @SaCheckPermission("system:post:delete")
    @DeleteMapping("/{id}")
    public R delete(@PathVariable("id") Long id) {
        boolean bool = postService.removeById(id);
        return R.ajax(bool);
    }

    @GetMapping("/{id}")
    public PostDO getById(@PathVariable("id") Long id) {
        return postService.getById(id);
    }

    @SaCheckPermission("system:post:detail")
    @GetMapping("/detail/{id}")
    public PostDTO detailById(@PathVariable("id") Long id) {
        return postService.detailById(id);
    }

    @SaCheckPermission("system:post:list")
    @GetMapping("/page")
    public IPage<PostDTO> page(Page<PostDTO> page, PostDTO dto) {
        return postService.page(page, dto);
    }

    @GetMapping("/list")
    public List<PostDO> list() {
        return postService.lambdaQuery().eq(PostDO::getDisabled, 0).list();
    }

    @SaCheckPermission("system:post:disabled")
    @PostMapping("/setDisabledValue/{id}")
    public R setDisabledValue(@PathVariable("id") Long id, boolean disabled) {
        boolean bool = postService.setDisabledValue(id, disabled);
        return R.ajax(bool);
    }

    @SaCheckPermission("system:post:assignUser")
    @PostMapping("/addUser/{id}")
    public R addUsers(@PathVariable("id") Long id, @RequestParam(value = "userIdList[]") Long[] userIdList) {
        postService.addUsers(id, ListUtil.of(userIdList));
        return R.success();
    }

    @DeleteMapping("/removeUser/{id}")
    public R removeUsers(@PathVariable("id") Long id, @RequestParam(value = "userIdList[]") Long[] userIdList) {
        postService.removeUsers(id, Arrays.asList(userIdList));
        return R.success();
    }

    @SaCheckPermission("system:post:authorization")
    @PostMapping("/setMenu/{id}")
    public R setMenu(@PathVariable("id") Long id, @RequestParam(value = "menuIdList[]", required = false) Long[] menuIdList) {
        postService.setMenu(id, ListUtil.of(menuIdList));
        return R.success();
    }

}

package com.github.mengweijin.vitality.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.mengweijin.vitality.framework.domain.R;
import com.github.mengweijin.vitality.system.domain.entity.Post;
import com.github.mengweijin.vitality.system.service.PostService;
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
 *  Post Controller
 * </p>
 *
 * @author mengweijin
 * @since 2023-06-03
 */
@Slf4j
@AllArgsConstructor
@Validated
@RestController
@RequestMapping("/system/post")
public class PostController {

    private PostService postService;

    /**
     * <p>
     * Get Post page by Post
     * </p>
     * @param page page
     * @param post {@link Post}
     * @return Page<Post>
     */
    @SaCheckPermission("system:post:query")
    @GetMapping("/page")
    public IPage<Post> page(Page<Post> page, Post post) {
        return postService.page(page, post);
    }

    /**
     * <p>
     * Get Post list by Post
     * </p>
     * @param post {@link Post}
     * @return List<Post>
     */
    @SaCheckPermission("system:post:query")
    @GetMapping("/list")
    public List<Post> list(Post post) {
        return postService.list(new LambdaQueryWrapper<>(post));
    }

    /**
     * <p>
     * Get Post by id
     * </p>
     * @param id id
     * @return Post
     */
    @SaCheckPermission("system:post:query")
    @GetMapping("/{id}")
    public Post getById(@PathVariable("id") Long id) {
        return postService.getById(id);
    }

    /**
     * <p>
     * Add Post
     * </p>
     * @param post {@link Post}
     */
    @SaCheckPermission("system:post:create")
    @PostMapping("/create")
    public R<Void> create(@Valid @RequestBody Post post) {
        boolean bool = postService.save(post);
        return R.ajax(bool);
    }

    /**
     * <p>
     * Update Post
     * </p>
     * @param post {@link Post}
     */
    @SaCheckPermission("system:post:update")
    @PostMapping("/update")
    public R<Void> update(@Valid @RequestBody Post post) {
        boolean bool = postService.updateById(post);
        return R.ajax(bool);
    }

    /**
     * <p>
     * Delete Post by id(s), Multiple ids can be separated by commas ",".
     * </p>
     * @param ids id
     */
    @SaCheckPermission("system:post:delete")
    @PostMapping("/delete/{ids}")
    public R<Void> delete(@PathVariable("ids") Long[] ids) {
        int i = postService.getBaseMapper().deleteByIds(Arrays.asList(ids));
        return R.ajax(i);
    }

}


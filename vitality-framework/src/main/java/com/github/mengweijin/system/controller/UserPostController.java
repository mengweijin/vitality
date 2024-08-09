package com.github.mengweijin.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.mengweijin.framework.domain.R;
import com.github.mengweijin.system.domain.entity.UserPost;
import com.github.mengweijin.system.service.UserPostService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

/**
 * <p>
 *  UserPost Controller
 * </p>
 *
 * @author mengweijin
 * @since 2023-06-03
 */
@Slf4j
@AllArgsConstructor
@Validated
@RestController
@RequestMapping("/system/user-post")
public class UserPostController {

    private UserPostService userPostService;

    /**
     * <p>
     * Get UserPost page by UserPost
     * </p>
     * @param page page
     * @param userPost {@link UserPost}
     * @return Page<UserPost>
     */
    @SaCheckPermission("system:userPost:query")
    @GetMapping("/page")
    public IPage<UserPost> page(Page<UserPost> page, UserPost userPost) {
        return userPostService.page(page, userPost);
    }

    /**
     * <p>
     * Get UserPost list by UserPost
     * </p>
     * @param userPost {@link UserPost}
     * @return List<UserPost>
     */
    @SaCheckPermission("system:userPost:query")
    @GetMapping("/list")
    public List<UserPost> list(UserPost userPost) {
        return userPostService.list(new QueryWrapper<>(userPost));
    }

    /**
     * <p>
     * Get UserPost by id
     * </p>
     * @param id id
     * @return UserPost
     */
    @SaCheckPermission("system:userPost:query")
    @GetMapping("/{id}")
    public UserPost getById(@PathVariable("id") Long id) {
        return userPostService.getById(id);
    }

    /**
     * <p>
     * Add UserPost
     * </p>
     * @param userPost {@link UserPost}
     */
    @SaCheckPermission("system:userPost:create")
    @PostMapping
    public R<Void> add(@Valid @RequestBody UserPost userPost) {
        boolean bool = userPostService.save(userPost);
        return R.ajax(bool);
    }

    /**
     * <p>
     * Update UserPost
     * </p>
     * @param userPost {@link UserPost}
     */
    @SaCheckPermission("system:userPost:update")
    @PutMapping
    public R<Void> update(@Valid @RequestBody UserPost userPost) {
        boolean bool = userPostService.updateById(userPost);
        return R.ajax(bool);
    }

    /**
     * <p>
     * Delete UserPost by id(s), Multiple ids can be separated by commas ",".
     * </p>
     * @param ids id
     */
    @SaCheckPermission("system:userPost:delete")
    @DeleteMapping("/{ids}")
    public R<Void> delete(@PathVariable("ids") Long[] ids) {
        int i = userPostService.getBaseMapper().deleteByIds(Arrays.asList(ids));
        return R.ajax(i);
    }

}


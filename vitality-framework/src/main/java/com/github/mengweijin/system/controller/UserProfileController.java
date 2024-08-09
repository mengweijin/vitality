package com.github.mengweijin.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.mengweijin.framework.domain.R;
import com.github.mengweijin.system.domain.entity.UserProfile;
import com.github.mengweijin.system.service.UserProfileService;
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
 *  UserProfile Controller
 * </p>
 *
 * @author mengweijin
 * @since 2023-06-03
 */
@Slf4j
@AllArgsConstructor
@Validated
@RestController
@RequestMapping("/system/user-profile")
public class UserProfileController {

    private UserProfileService userProfileService;

    /**
     * <p>
     * Get UserProfile page by UserProfile
     * </p>
     * @param page page
     * @param userProfile {@link UserProfile}
     * @return Page<UserProfile>
     */
    @SaCheckPermission("system:userProfile:query")
    @GetMapping("/page")
    public IPage<UserProfile> page(Page<UserProfile> page, UserProfile userProfile) {
        return userProfileService.page(page, userProfile);
    }

    /**
     * <p>
     * Get UserProfile list by UserProfile
     * </p>
     * @param userProfile {@link UserProfile}
     * @return List<UserProfile>
     */
    @SaCheckPermission("system:userProfile:query")
    @GetMapping("/list")
    public List<UserProfile> list(UserProfile userProfile) {
        return userProfileService.list(new QueryWrapper<>(userProfile));
    }

    /**
     * <p>
     * Get UserProfile by id
     * </p>
     * @param id id
     * @return UserProfile
     */
    @SaCheckPermission("system:userProfile:query")
    @GetMapping("/{id}")
    public UserProfile getById(@PathVariable("id") Long id) {
        return userProfileService.getById(id);
    }

    /**
     * <p>
     * Add UserProfile
     * </p>
     * @param userProfile {@link UserProfile}
     */
    @SaCheckPermission("system:userProfile:create")
    @PostMapping
    public R<Void> add(@Valid @RequestBody UserProfile userProfile) {
        boolean bool = userProfileService.save(userProfile);
        return R.ajax(bool);
    }

    /**
     * <p>
     * Update UserProfile
     * </p>
     * @param userProfile {@link UserProfile}
     */
    @SaCheckPermission("system:userProfile:update")
    @PutMapping
    public R<Void> update(@Valid @RequestBody UserProfile userProfile) {
        boolean bool = userProfileService.updateById(userProfile);
        return R.ajax(bool);
    }

    /**
     * <p>
     * Delete UserProfile by id(s), Multiple ids can be separated by commas ",".
     * </p>
     * @param ids id
     */
    @SaCheckPermission("system:userProfile:delete")
    @DeleteMapping("/{ids}")
    public R<Void> delete(@PathVariable("ids") Long[] ids) {
        int i = userProfileService.getBaseMapper().deleteByIds(Arrays.asList(ids));
        return R.ajax(i);
    }

}


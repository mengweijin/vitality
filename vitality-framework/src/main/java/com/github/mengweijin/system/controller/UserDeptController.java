package com.github.mengweijin.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.mengweijin.framework.domain.R;
import com.github.mengweijin.system.domain.entity.UserDept;
import com.github.mengweijin.system.service.UserDeptService;
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
 *  UserDept Controller
 * </p>
 *
 * @author mengweijin
 * @since 2023-06-03
 */
@Slf4j
@AllArgsConstructor
@Validated
@RestController
@RequestMapping("/system/user-dept")
public class UserDeptController {

    private UserDeptService userDeptService;

    /**
     * <p>
     * Get UserDept page by UserDept
     * </p>
     * @param page page
     * @param userDept {@link UserDept}
     * @return Page<UserDept>
     */
    @SaCheckPermission("system:userDept:query")
    @GetMapping("/page")
    public IPage<UserDept> page(Page<UserDept> page, UserDept userDept) {
        return userDeptService.page(page, userDept);
    }

    /**
     * <p>
     * Get UserDept list by UserDept
     * </p>
     * @param userDept {@link UserDept}
     * @return List<UserDept>
     */
    @SaCheckPermission("system:userDept:query")
    @GetMapping("/list")
    public List<UserDept> list(UserDept userDept) {
        return userDeptService.list(new QueryWrapper<>(userDept));
    }

    /**
     * <p>
     * Get UserDept by id
     * </p>
     * @param id id
     * @return UserDept
     */
    @SaCheckPermission("system:userDept:query")
    @GetMapping("/{id}")
    public UserDept getById(@PathVariable("id") Long id) {
        return userDeptService.getById(id);
    }

    /**
     * <p>
     * Add UserDept
     * </p>
     * @param userDept {@link UserDept}
     */
    @SaCheckPermission("system:userDept:create")
    @PostMapping
    public R<Void> add(@Valid @RequestBody UserDept userDept) {
        boolean bool = userDeptService.save(userDept);
        return R.ajax(bool);
    }

    /**
     * <p>
     * Update UserDept
     * </p>
     * @param userDept {@link UserDept}
     */
    @SaCheckPermission("system:userDept:update")
    @PutMapping
    public R<Void> update(@Valid @RequestBody UserDept userDept) {
        boolean bool = userDeptService.updateById(userDept);
        return R.ajax(bool);
    }

    /**
     * <p>
     * Delete UserDept by id(s), Multiple ids can be separated by commas ",".
     * </p>
     * @param ids id
     */
    @SaCheckPermission("system:userDept:delete")
    @DeleteMapping("/{ids}")
    public R<Void> delete(@PathVariable("ids") Long[] ids) {
        int i = userDeptService.getBaseMapper().deleteByIds(Arrays.asList(ids));
        return R.ajax(i);
    }

}


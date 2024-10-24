package com.github.mengweijin.vitality.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaMode;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.mengweijin.vitality.framework.domain.R;
import com.github.mengweijin.vitality.framework.log.aspect.annotation.Log;
import com.github.mengweijin.vitality.framework.log.aspect.enums.EOperationType;
import com.github.mengweijin.vitality.framework.validator.group.Group;
import com.github.mengweijin.vitality.system.domain.entity.UserPreference;
import com.github.mengweijin.vitality.system.service.UserPreferenceService;
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
 * UserPreference Controller
 * </p>
 *
 * @author mengweijin
 * @since 2023-06-03
 */
@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/system/user-preference")
public class UserPreferenceController {

    private UserPreferenceService userPreferenceService;

    /**
     * <p>
     * Get UserPreference page by UserPreference
     * </p>
     *
     * @param page           page
     * @param userPreference {@link UserPreference}
     * @return Page<UserPreference>
     */
    @SaCheckPermission("system:userPreference:query")
    @GetMapping("/page")
    public IPage<UserPreference> page(Page<UserPreference> page, UserPreference userPreference) {
        return userPreferenceService.page(page, userPreference);
    }

    /**
     * <p>
     * Get UserPreference list by UserPreference
     * </p>
     *
     * @param userPreference {@link UserPreference}
     * @return List<UserPreference>
     */
    @SaCheckPermission("system:userPreference:query")
    @GetMapping("/list")
    public List<UserPreference> list(UserPreference userPreference) {
        return userPreferenceService.list(new QueryWrapper<>(userPreference));
    }

    /**
     * <p>
     * Get UserPreference by id
     * </p>
     *
     * @param id id
     * @return UserPreference
     */
    @SaCheckPermission("system:userPreference:query")
    @GetMapping("/{id}")
    public UserPreference getById(@PathVariable("id") Long id) {
        return userPreferenceService.getById(id);
    }

    /**
     * <p>
     * Get UserPreference by userId
     * </p>
     *
     * @return UserPreference
     */
    @SaCheckPermission("system:userPreference:query")
    @GetMapping("/query")
    public UserPreference getUserPreference() {
        return userPreferenceService.getUserPreferenceByLoginUser();
    }

    /**
     * <p>
     * Add UserPreference
     * </p>
     *
     * @param userPreference {@link UserPreference}
     */
    @Log(operationType = EOperationType.INSERT)
    @SaCheckPermission("system:userPreference:create")
    @PostMapping
    public R<Void> add(@Validated({Group.Default.class, Group.Create.class}) @RequestBody UserPreference userPreference) {
        boolean bool = userPreferenceService.save(userPreference);
        return R.ajax(bool);
    }

    /**
     * <p>
     * Update UserPreference
     * </p>
     *
     * @param userPreference {@link UserPreference}
     */
    @Log(operationType = EOperationType.UPDATE)
    @SaCheckPermission("system:userPreference:update")
    @PutMapping
    public R<Void> update(@Validated({Group.Default.class, Group.Update.class}) @RequestBody UserPreference userPreference) {
        boolean bool = userPreferenceService.updateById(userPreference);
        return R.ajax(bool);
    }

    /**
     * <p>
     * Save UserPreference
     * </p>
     *
     * @param userPreference {@link UserPreference}
     */
    @Log(operationType = EOperationType.UPDATE)
    @SaCheckPermission(value = {"system:userPreference:create", "system:userPreference:update"}, mode = SaMode.OR)
    @PostMapping("/save")
    public R<Void> saveOrUpdate(@Validated @RequestBody UserPreference userPreference) {
        boolean bool = userPreferenceService.saveOrUpdateByLoginUser(userPreference);
        return R.ajax(bool);
    }

    /**
     * <p>
     * Delete UserPreference by id(s), Multiple ids can be separated by commas ",".
     * </p>
     *
     * @param ids id
     */
    @Log(operationType = EOperationType.DELETE)
    @SaCheckPermission("system:userPreference:delete")
    @DeleteMapping("/{ids}")
    public R<Void> delete(@PathVariable("ids") Long[] ids) {
        boolean bool = userPreferenceService.removeByIds(Arrays.asList(ids));
        return R.ajax(bool);
    }

}


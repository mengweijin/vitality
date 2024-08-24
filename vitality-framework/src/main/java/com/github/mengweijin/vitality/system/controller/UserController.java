package com.github.mengweijin.vitality.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.mengweijin.vitality.framework.domain.R;
import com.github.mengweijin.vitality.framework.util.BeanUtils;
import com.github.mengweijin.vitality.system.domain.entity.User;
import com.github.mengweijin.vitality.system.domain.vo.UserVO;
import com.github.mengweijin.vitality.system.service.UserService;
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
 *  User Controller
 * </p>
 *
 * @author mengweijin
 * @since 2023-06-03
 */
@Slf4j
@AllArgsConstructor
@Validated
@RestController
@RequestMapping("/system/user")
public class UserController {

    private UserService userService;

    /**
     * <p>
     * Get User page by User
     * </p>
     * @param page page
     * @param user {@link User}
     * @return Page<User>
     */
    @SaCheckPermission("system:user:query")
    @GetMapping("/page")
    public IPage<UserVO> page(Page<User> page, User user) {
        IPage<User> userPage = userService.page(page, user);
        return BeanUtils.copyPage(userPage, UserVO.class);
    }

    /**
     * <p>
     * Get User list by User
     * </p>
     * @param user {@link User}
     * @return List<User>
     */
    @SaCheckPermission("system:user:query")
    @GetMapping("/list")
    public List<UserVO> list(User user) {
        List<User> userList = userService.list(new QueryWrapper<>(user));
        return BeanUtils.copyList(userList, UserVO.class);
    }

    /**
     * <p>
     * Get User by id
     * </p>
     * @param id id
     * @return User
     */
    @SaCheckPermission("system:user:query")
    @GetMapping("/{id}")
    public UserVO getById(@PathVariable("id") Long id) {
        User user = userService.getById(id);
        return BeanUtils.copyBean(user, UserVO.class);
    }

    /**
     * <p>
     * Add User
     * </p>
     * @param user {@link User}
     */
    @SaCheckPermission("system:user:create")
    @PostMapping
    public R<Void> add(@Valid @RequestBody User user) {
        boolean bool = userService.save(user);
        return R.ajax(bool);
    }

    /**
     * <p>
     * Update User
     * </p>
     * @param user {@link User}
     */
    @SaCheckPermission("system:user:update")
    @PutMapping
    public R<Void> update(@Valid @RequestBody User user) {
        boolean bool = userService.updateById(user);
        return R.ajax(bool);
    }

    /**
     * <p>
     * Delete User by id(s), Multiple ids can be separated by commas ",".
     * </p>
     * @param ids id
     */
    @SaCheckPermission("system:user:delete")
    @DeleteMapping("/{ids}")
    public R<Void> delete(@PathVariable("ids") Long[] ids) {
        int i = userService.getBaseMapper().deleteByIds(Arrays.asList(ids));
        return R.ajax(i);
    }

}


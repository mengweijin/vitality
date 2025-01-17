package com.github.mengweijin.vitality.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.mengweijin.vitality.framework.domain.R;
import com.github.mengweijin.vitality.framework.log.aspect.annotation.Log;
import com.github.mengweijin.vitality.framework.log.aspect.enums.EOperationType;
import com.github.mengweijin.vitality.framework.validator.group.Group;
import com.github.mengweijin.vitality.system.domain.entity.Category;
import com.github.mengweijin.vitality.system.service.CategoryService;
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
 * Category Controller
 *
 * @author mengweijin
 */
@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/system/category")
public class CategoryController {

    private CategoryService categoryService;

    /**
     * Get Category page by Category
     *
     * @param page     page
     * @param category {@link Category}
     * @return Page<Category>
     */
    @SaCheckPermission("system:category:query")
    @GetMapping("/page")
    public IPage<Category> page(Page<Category> page, Category category) {
        return categoryService.page(page, category);
    }

    /**
     * Get Category list by Category
     *
     * @param category {@link Category}
     * @return List<Category>
     */
    @SaCheckPermission("system:category:query")
    @GetMapping("/list")
    public List<Category> list(Category category) {
        return categoryService.list(new LambdaQueryWrapper<>(category));
    }

    /**
     * Get Category by id
     *
     * @param id id
     * @return Category
     */
    @SaCheckPermission("system:category:query")
    @GetMapping("/{id}")
    public Category getById(@PathVariable("id") Long id) {
        return categoryService.getById(id);
    }

    /**
     * Add Category
     *
     * @param category {@link Category}
     */
    @Log(operationType = EOperationType.INSERT)
    @SaCheckPermission("system:category:create")
    @PostMapping("/create")
    public R<Void> create(@Validated({Group.Default.class, Group.Create.class}) @RequestBody Category category) {
        boolean bool = categoryService.save(category);
        return R.ajax(bool);
    }

    /**
     * Update Category
     *
     * @param category {@link Category}
     */
    @Log(operationType = EOperationType.UPDATE)
    @SaCheckPermission("system:category:update")
    @PostMapping("/update")
    public R<Void> update(@Validated({Group.Default.class, Group.Update.class}) @RequestBody Category category) {
        boolean bool = categoryService.updateById(category);
        return R.ajax(bool);
    }

    /**
     * Delete Category by id(s), Multiple ids can be separated by commas ",".
     *
     * @param ids id
     */
    @Log(operationType = EOperationType.DELETE)
    @SaCheckPermission("system:category:delete")
    @PostMapping("/delete/{ids}")
    public R<Void> delete(@PathVariable("ids") Long[] ids) {
        boolean bool = categoryService.removeByIds(Arrays.asList(ids));
        return R.ajax(bool);
    }

}



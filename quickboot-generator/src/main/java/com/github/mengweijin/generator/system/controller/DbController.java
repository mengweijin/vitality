package com.github.mengweijin.generator.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.PageDTO;
import com.github.mengweijin.generator.system.entity.Db;
import com.github.mengweijin.generator.system.service.DbService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author mengweijin
 * @since 2022-05-03
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/db")
public class DbController {

    @Autowired
    private DbService dbService;

    @GetMapping("/page")
    public PageDTO<Db> page(PageDTO<Db> page, Db db) {
        return dbService.page(page, new QueryWrapper<>(db));
    }

    @GetMapping("/{id}")
    public Db getById(@PathVariable("id") Long id) {
        return dbService.getById(id);
    }

    @PostMapping
    public void add(@Valid Db db) {
        dbService.save(db);
    }

    @PutMapping
    public void update(@Valid Db db) {
        dbService.updateById(db);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        dbService.removeById(id);
    }

}


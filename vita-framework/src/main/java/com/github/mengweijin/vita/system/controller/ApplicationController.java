package com.github.mengweijin.vita.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.github.mengweijin.vita.system.domain.vo.application.MonitorVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author mengweijin
 * @since 2022/10/30
 */
@RestController
@RequestMapping("/system/application")
public class ApplicationController {

    @SaCheckPermission("system:application:query")
    @GetMapping
    public MonitorVO serverInfo() {
        return new MonitorVO();
    }

}
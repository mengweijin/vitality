package com.github.mengweijin.vitality.monitor.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.github.mengweijin.vitality.monitor.domain.vo.application.MonitorVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author mengweijin
 * @since 2022/10/30
 */
@RestController
@RequestMapping("/monitor/application")
public class ApplicationController {

    @SaCheckPermission("monitor:application:query")
    @GetMapping
    public MonitorVO serverInfo() {
        return new MonitorVO();
    }

}
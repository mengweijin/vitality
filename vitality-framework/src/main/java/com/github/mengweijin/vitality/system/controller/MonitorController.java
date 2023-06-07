package com.github.mengweijin.vitality.system.controller;

import com.github.mengweijin.vitality.system.dto.monitor.MonitorDTO;
import com.github.mengweijin.vitality.system.service.MonitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author mengweijin
 * @date 2022/10/30
 */
@RestController
@RequestMapping("/monitor")
public class MonitorController {

    @Autowired
    private MonitorService monitorService;

    @GetMapping("/serverInfo")
    public MonitorDTO serverInfo() {
        return monitorService.getServerInfo();
    }

}

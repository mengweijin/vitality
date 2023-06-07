package com.github.mengweijin.vitality.system.service;

import com.github.mengweijin.vitality.system.dto.monitor.MonitorDTO;
import org.dromara.hutool.extra.management.oshi.OshiUtil;
import org.springframework.stereotype.Service;
import oshi.hardware.HWDiskStore;

import java.util.List;

/**
 * @author mengweijin
 * @date 2023/6/7
 */
@Service
public class MonitorService {

    public MonitorDTO getServerInfo() {
        List<HWDiskStore> diskStores = OshiUtil.getDiskStores();
        MonitorDTO dto = new MonitorDTO();
        return dto;
    }
}

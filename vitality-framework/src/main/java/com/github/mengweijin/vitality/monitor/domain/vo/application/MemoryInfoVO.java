package com.github.mengweijin.vitality.monitor.domain.vo.application;

import lombok.Data;
import org.dromara.hutool.extra.management.oshi.OshiUtil;
import oshi.hardware.GlobalMemory;
import oshi.hardware.PhysicalMemory;

import java.util.List;

/**
 * @author mengweijin
 * @since 2023/6/8
 */
@Data
public class MemoryInfoVO {

    private String global;

    private List<String> physical;

    private String virtual;

    public MemoryInfoVO() {
        GlobalMemory memory = OshiUtil.getMemory();
        global = memory.toString();
        physical = memory.getPhysicalMemory().stream().map(PhysicalMemory::toString).toList();
        virtual = memory.getVirtualMemory().toString();
    }
}

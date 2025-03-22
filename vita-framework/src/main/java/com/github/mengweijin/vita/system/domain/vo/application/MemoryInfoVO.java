package com.github.mengweijin.vita.system.domain.vo.application;

import lombok.Data;
import org.dromara.hutool.extra.management.oshi.OshiUtil;
import oshi.hardware.GlobalMemory;
import oshi.hardware.PhysicalMemory;

import java.util.stream.Collectors;

/**
 * @author mengweijin
 * @since 2023/6/8
 */
@Data
public class MemoryInfoVO {

    private String global;

    private String physical;

    private String virtual;

    public MemoryInfoVO() {
        GlobalMemory memory = OshiUtil.getMemory();
        global = memory.toString();
        physical = memory.getPhysicalMemory().stream().map(PhysicalMemory::toString)
                .collect(Collectors.joining("\n"));
        virtual = memory.getVirtualMemory().toString();
    }
}

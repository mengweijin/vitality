package com.github.mengweijin.system.dto.monitor;

import lombok.Data;
import org.dromara.hutool.extra.management.oshi.OshiUtil;
import oshi.hardware.GlobalMemory;

/**
 * @author mengweijin
 * @date 2023/6/8
 */
@Data
public class MemoryInfoDTO {

    /**
     * The amount of actual physical memory, in bytes.
     */
    long total;

    /**
     * The amount of physical memory currently available, in bytes.
     */
    long available;

    long unavailable;

    public MemoryInfoDTO() {
        GlobalMemory memory = OshiUtil.getMemory();
        this.setTotal(memory.getTotal());
        this.setAvailable(memory.getAvailable());
        this.setUnavailable(this.total - this.available);
    }
}

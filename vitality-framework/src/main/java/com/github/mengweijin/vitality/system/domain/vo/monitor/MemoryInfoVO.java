package com.github.mengweijin.vitality.system.domain.vo.monitor;

import lombok.Data;
import org.dromara.hutool.extra.management.oshi.OshiUtil;
import oshi.hardware.GlobalMemory;

/**
 * @author mengweijin
 * @since 2023/6/8
 */
@Data
public class MemoryInfoVO {

    /**
     * The amount of actual physical memory, in bytes.
     */
    private long total;

    /**
     * The amount of physical memory currently available, in bytes.
     */
    private long available;

    private long unavailable;

    public MemoryInfoVO() {
        GlobalMemory memory = OshiUtil.getMemory();
        this.setTotal(memory.getTotal());
        this.setAvailable(memory.getAvailable());
        this.setUnavailable(this.total - this.available);
    }
}

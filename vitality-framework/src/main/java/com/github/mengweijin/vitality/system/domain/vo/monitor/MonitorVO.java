package com.github.mengweijin.vitality.system.domain.vo.monitor;

import lombok.Data;

import java.io.Serializable;

/**
 * @author mengweijin
 * @since 2023/6/7
 */
@Data
public class MonitorVO implements Serializable {

    private ServerInfoVO server = new ServerInfoVO();

    private CpuInfoVO cpu = new CpuInfoVO();

    private MemoryInfoVO memory = new MemoryInfoVO();

    private JvmInfoVO jvm = new JvmInfoVO();

    private DiskInfoVO disk = new DiskInfoVO();

}


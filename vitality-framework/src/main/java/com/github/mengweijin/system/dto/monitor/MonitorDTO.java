package com.github.mengweijin.system.dto.monitor;

import lombok.Data;

import java.io.Serializable;

/**
 * @author mengweijin
 * @date 2023/6/7
 */
@Data
public class MonitorDTO implements Serializable {

    private ServerInfoDTO server = new ServerInfoDTO();

    private CpuInfoDTO cpu = new CpuInfoDTO();

    private MemoryInfoDTO memory = new MemoryInfoDTO();

    private JvmInfoDTO jvm = new JvmInfoDTO();

    private DiskInfoDTO disk = new DiskInfoDTO();

}

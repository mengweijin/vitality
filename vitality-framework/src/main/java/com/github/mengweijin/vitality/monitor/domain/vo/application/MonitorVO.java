package com.github.mengweijin.vitality.monitor.domain.vo.application;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author mengweijin
 * @since 2023/6/7
 */
@Data
public class MonitorVO implements Serializable {

    private static ServerInfoVO server = new ServerInfoVO();

    private CpuInfoVO cpu = new CpuInfoVO();

    private MemoryInfoVO memory = new MemoryInfoVO();

    private JvmInfoVO jvm = new JvmInfoVO();

    private List<DiskInfoVO> disk = DiskInfoVO.init();

}


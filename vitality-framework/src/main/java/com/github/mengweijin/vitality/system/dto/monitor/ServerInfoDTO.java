package com.github.mengweijin.vitality.system.dto.monitor;

import lombok.Data;
import org.dromara.hutool.extra.management.HostInfo;
import org.dromara.hutool.extra.management.OsInfo;
import org.dromara.hutool.extra.management.oshi.OshiUtil;

/**
 * @author mengweijin
 * @date 2023/6/8
 */
@Data
public class ServerInfoDTO {

    private String manufacturer = OshiUtil.getSystem().getManufacturer();

    private String hostName;

    private String hostAddress;

    private String operationSystem = OshiUtil.getOs().toString();

    private String operationSystemArch = new OsInfo().getArch();

    public ServerInfoDTO() {
        HostInfo hostInfo = new HostInfo();
        this.hostName = hostInfo.getName();
        this.hostAddress = hostInfo.getAddress();
    }
}

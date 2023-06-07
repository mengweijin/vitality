package com.github.mengweijin.vitality.system.dto.monitor;

import lombok.Data;
import org.dromara.hutool.core.io.file.FileUtil;
import org.dromara.hutool.extra.management.HostInfo;
import org.dromara.hutool.extra.management.JavaRuntimeInfo;
import org.dromara.hutool.extra.management.JvmInfo;
import org.dromara.hutool.extra.management.OsInfo;
import org.dromara.hutool.extra.management.RuntimeInfo;
import org.dromara.hutool.extra.management.UserInfo;
import org.dromara.hutool.extra.management.oshi.CpuInfo;
import org.dromara.hutool.extra.management.oshi.OshiUtil;
import oshi.hardware.GlobalMemory;
import java.io.Serializable;

/**
 * @author mengweijin
 * @date 2023/6/7
 */
@Data
public class MonitorDTO implements Serializable {

    private ServerDTO server = new ServerDTO();

    private CpuDTO cpu = new CpuDTO();

    private MemoryDTO memory = new MemoryDTO();

    private JvmDTO jvm = new JvmDTO();

    @Data
    private static class CpuDTO {

        /**
         * CPU核心数
         */
        private Integer cpuNum;

        /**
         * CPU总的使用率
         */
        private double total;

        /**
         * CPU系统使用率
         */
        private double sys;

        /**
         * CPU用户使用率
         */
        private double user;

        /**
         * CPU当前等待率
         */
        private double wait;

        /**
         * CPU当前空闲率
         */
        private double free;

        /**
         * CPU型号信息
         */
        private String cpuModel;

        public CpuDTO() {
            CpuInfo cpuInfo = OshiUtil.getCpuInfo();
            this.cpuNum = cpuInfo.getCpuNum();
            this.total = cpuInfo.getToTal();
            this.sys = cpuInfo.getSys();
            this.user = cpuInfo.getUser();
            this.wait = cpuInfo.getWait();
            this.free = cpuInfo.getFree();
            this.cpuModel = cpuInfo.getCpuModel();
        }

    }

    @Data
    private static class MemoryDTO {
        /**
         * The amount of actual physical memory, in bytes.
         */
        long total;

        /**
         * The amount of physical memory currently available, in bytes.
         */
        long available;

        long unavailable;

        public MemoryDTO() {
            GlobalMemory memory = OshiUtil.getMemory();
            this.setTotal(memory.getTotal());
            this.setAvailable(memory.getAvailable());
            this.setUnavailable(this.total - this.available);
        }
    }

    @Data
    private static class ServerDTO {

        private String manufacturer;

        private String hostName;

        private String hostAddress;

        private String operationSystem = OshiUtil.getOs().toString();

        private String operationSystemArch = new OsInfo().getArch();

        public ServerDTO() {
            this.manufacturer =  OshiUtil.getSystem().getManufacturer();
            HostInfo hostInfo = new HostInfo();
            this.hostName = hostInfo.getName();
            this.hostAddress = hostInfo.getAddress();
        }
    }

    @Data
    private static class JvmDTO {
        private String jvmName;
        private String jvmVersion;
        private String jvmVendor;
        private String javaHome;
        private String projectHome;

        private String maxMemory;
        private String totalMemory;
        private String freeMemory;
        private String usableMemory;
        public JvmDTO() {
            JvmInfo jvmInfo = new JvmInfo();
            this.jvmName = jvmInfo.getName();
            this.jvmVersion = jvmInfo.getVersion();
            this.jvmVendor = jvmInfo.getVendor();
            this.javaHome = new JavaRuntimeInfo().getHomeDir();
            this.projectHome = new UserInfo().getCurrentDir();

            RuntimeInfo runtimeInfo = new RuntimeInfo();
            this.maxMemory = FileUtil.readableFileSize(runtimeInfo.getMaxMemory());
            this.totalMemory = FileUtil.readableFileSize(runtimeInfo.getTotalMemory());
            this.freeMemory = FileUtil.readableFileSize(runtimeInfo.getFreeMemory());
            this.usableMemory = FileUtil.readableFileSize(runtimeInfo.getUsableMemory());
        }
    }
}

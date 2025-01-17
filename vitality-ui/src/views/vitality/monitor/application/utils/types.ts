interface ServerInfoVO {
  /** 厂商 */
  manufacturer?: string;
  /** 主机名 */
  hostName?: string;
  /** 主机地址 */
  hostAddress?: string;
  /** 操作系统 */
  operationSystem?: string;
  /** 系统架构 */
  operationSystemArch?: string;
}

interface CpuInfoVO {
  /** CPU核心数 */
  cpuNum?: number;
  /** CPU总的使用率 */
  total?: number;
  /** CPU系统使用率 */
  sys?: number;
  /** CPU用户使用率 */
  user?: number;
  /** CPU当前等待率 */
  wait?: number;
  /** CPU当前空闲率 */
  free?: number;
  /** CPU型号信息 */
  cpuModel?: string;
}

interface MemoryInfoVO {
  /** 整体内存 */
  global?: string;
  /** 物理内存 */
  physical?: string[];
  /** 虚拟内存 */
  virtual?: string;
}

interface JvmInfoVO {
  /** JVM 名称 */
  jvmName?: string;
  /** JVM 版本 */
  jvmVersion?: string[];
  /** JVM 供应商 */
  jvmVendor?: string;
  /** JDK 安装位置 */
  javaHome?: string;
  /** 应用安装位置 */
  projectHome?: string;
  /** 应用启动时间 */
  startTime?: number;
  /** 应用已运行时间（单位：秒） */
  runTimeSeconds?: number;
  /** 应用进程 ID */
  currentProcessIdentifier?: number;
  /** JVM 最大内存 */
  maxMemory?: string;
  /** JVM 总内存 */
  totalMemory?: string;
  /** JVM 空闲内存 */
  freeMemory?: string;
  /** JVM 已使用内存 */
  usableMemory?: string;
  /** 应用启动参数 */
  startArgs?: string;
}

interface DiskInfoVO {
  /** 卷名称 */
  mountName?: string;
  /** 磁盘格式 */
  diskFormat?: string;
  /** 磁盘名称 */
  diskName?: string;
  /** 磁盘总容量 */
  total?: string;
  /** 磁盘剩余容量 */
  free?: string;
  /** 磁盘已使用容量 */
  used?: string;
  /** 磁盘使用率 */
  usageRate?: string;
}

interface MonitorVO {
  server?: ServerInfoVO;
  cpu?: CpuInfoVO;
  memory?: MemoryInfoVO;
  jvm?: JvmInfoVO;
  disk?: DiskInfoVO[];
}

export type {
  ServerInfoVO,
  CpuInfoVO,
  MemoryInfoVO,
  JvmInfoVO,
  DiskInfoVO,
  MonitorVO
};

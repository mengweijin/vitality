import { useMonitorApplication } from "./hook";

export function useDescriptionsColumns() {
  const serverColumns = [
    {
      label: "厂商",
      prop: "manufacturer",
      minWidth: 120
    },
    {
      label: "主机名",
      prop: "hostName",
      minWidth: 120
    },
    {
      label: "主机地址",
      prop: "hostAddress",
      copy: true
    },
    {
      label: "操作系统",
      prop: "operationSystem"
    },
    {
      label: "系统架构",
      prop: "operationSystemArch"
    }
  ];

  const cpuColumns = [
    {
      label: "CPU核心数",
      prop: "cpuNum",
      minWidth: 120
    },
    {
      label: "CPU总使用率",
      prop: "total",
      cellRenderer: ({ value }) => {
        return <span>{value}%</span>;
      },
      minWidth: 120,
      hide: () => true
    },
    {
      label: "CPU系统使用率",
      prop: "sys",
      cellRenderer: ({ value }) => {
        return <span>{value}%</span>;
      }
    },
    {
      label: "CPU用户使用率",
      prop: "user",
      cellRenderer: ({ value }) => {
        return <span>{value}%</span>;
      }
    },
    {
      label: "CPU当前等待率",
      prop: "wait",
      cellRenderer: ({ value }) => {
        return <span>{value}%</span>;
      }
    },
    {
      label: "CPU当前空闲率",
      prop: "free",
      cellRenderer: ({ value }) => {
        return <span>{value}%</span>;
      }
    },
    {
      label: "CPU型号信息",
      prop: "cpuModel",
      cellRenderer: ({ value }) => {
        return <span style="white-space: pre-wrap;">{value}</span>;
      }
    }
  ];

  const memoryColumns = [
    {
      label: "总内存",
      prop: "global",
      minWidth: 120
    },
    {
      label: "物理内存",
      prop: "physical",
      cellRenderer: ({ value }) => {
        return <span style="white-space: pre-wrap;">{value}</span>;
      },
      minWidth: 120
    },
    {
      label: "虚拟内存",
      prop: "virtual"
    }
  ];

  const jvmColumns = [
    {
      label: "虚拟机名称",
      prop: "jvmName",
      minWidth: 120
    },
    {
      label: "虚拟机版本",
      prop: "jvmVersion",
      minWidth: 120
    },
    {
      label: "虚拟机厂商",
      prop: "jvmVendor"
    },
    {
      label: "JAVA_HOME",
      prop: "javaHome"
    },
    {
      label: "应用安装位置",
      prop: "projectHome"
    },
    {
      label: "启动时间",
      prop: "startTime"
    },
    {
      label: "运行时间",
      prop: "runTimeSeconds",
      cellRenderer: ({ value }) => {
        return useMonitorApplication().secondsFormat(value);
      }
    },
    {
      label: "进程 ID",
      prop: "currentProcessIdentifier"
    },
    {
      label: "最大内存",
      prop: "maxMemory"
    },
    {
      label: "已分配内存",
      prop: "totalMemory"
    },
    {
      label: "空闲内存",
      prop: "freeMemory"
    },
    {
      label: "可用内存",
      prop: "usableMemory"
    },
    {
      label: "启动参数",
      prop: "startArgs"
    }
  ];

  return {
    serverColumns,
    cpuColumns,
    memoryColumns,
    jvmColumns
  };
}

import type { MonitorVO } from "../utils/types";
import { ref, onMounted } from "vue";
import { getMonitorVO } from "@/api/monitor/application";

export function useMonitorApplication() {
  const monitorVO = ref<MonitorVO>({});
  const loading = ref(true);

  const columns: TableColumnList = [
    {
      label: "卷名称",
      prop: "mountName",
      minWidth: 180,
      align: "left",
      hide: false
    },
    {
      label: "磁盘格式",
      prop: "diskFormat",
      minWidth: 180,
      hide: false
    },
    {
      label: "磁盘名称",
      prop: "diskName",
      minWidth: 180
    },
    {
      label: "磁盘总容量",
      prop: "total",
      minWidth: 180,
      hide: false
    },
    {
      label: "磁盘剩余容量",
      prop: "free",
      minWidth: 180,
      hide: false
    },
    {
      label: "磁盘已使用容量",
      prop: "used",
      minWidth: 180,
      hide: false
    },
    {
      label: "磁盘使用率",
      prop: "usageRate",
      minWidth: 100
    }
  ];

  async function onSearch() {
    loading.value = true;
    monitorVO.value = await getMonitorVO();

    setTimeout(() => {
      loading.value = false;
    }, 500);
  }

  function secondsFormat(seconds: number) {
    let daySec = 24 * 60 * 60;
    let hourSec = 60 * 60;
    let minuteSec = 60;
    let dd = Math.floor(seconds / daySec);
    let hh = Math.floor((seconds % daySec) / hourSec);
    let mm = Math.floor((seconds % hourSec) / minuteSec);
    let ss = seconds % minuteSec;
    if (dd > 0) {
      return dd + "天" + hh + "小时" + mm + "分钟" + ss + "秒";
    } else if (hh > 0) {
      return hh + "小时" + mm + "分钟" + ss + "秒";
    } else if (mm > 0) {
      return mm + "分钟" + ss + "秒";
    } else {
      return ss + "秒";
    }
  }

  onMounted(async () => {
    onSearch();
  });

  return {
    loading,
    columns,
    monitorVO,
    onSearch,
    secondsFormat
  };
}

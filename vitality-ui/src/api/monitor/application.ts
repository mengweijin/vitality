import { http } from "@/utils/http";
import type { MonitorVO } from "@/views/vitality/monitor/application/utils/types";

/** 查询 */
export const getMonitorVO = () => {
  return http.get<MonitorVO, any>("/monitor/application");
};

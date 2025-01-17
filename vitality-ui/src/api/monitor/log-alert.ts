import { http } from "@/utils/http";
import type {
  FormProps,
  LogAlertVO
} from "@/views/vitality/monitor/log-alert/utils/types";

/** 查询 */
export const getLogAlertPage = (params?: LogAlertVO) => {
  return http.get<Page, LogAlertVO>("/monitor/log-alert/page", { params });
};

/** 创建 */
export const createLogAlert = (data: FormProps) => {
  return http.post<R, FormProps>("/monitor/log-alert/create", { data });
};

/** 修改 */
export const updateLogAlert = (data: FormProps) => {
  return http.post<R, FormProps>("/monitor/log-alert/update", { data });
};

/** 删除 */
export const deleteLogAlert = (ids: String) => {
  return http.post<R, any>("/monitor/log-alert/delete/" + ids);
};

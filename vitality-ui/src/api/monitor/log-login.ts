import { http } from "@/utils/http";
import type {
  FormProps,
  LogLoginVO
} from "@/views/vitality/monitor/log-login/utils/types";

/** 查询 */
export const getLogLoginPage = (params?: LogLoginVO) => {
  return http.get<Page, LogLoginVO>("/monitor/log-login/page", { params });
};

/** 创建 */
export const createLogLogin = (data: FormProps) => {
  return http.post<R, FormProps>("/monitor/log-login/create", { data });
};

/** 修改 */
export const updateLogLogin = (data: FormProps) => {
  return http.post<R, FormProps>("/monitor/log-login/update", { data });
};

/** 删除 */
export const deleteLogLogin = (ids: String) => {
  return http.post<R, any>("/monitor/log-login/delete/" + ids);
};

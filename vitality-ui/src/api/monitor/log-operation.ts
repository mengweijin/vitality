import { http } from "@/utils/http";
import type {
  FormItemProps,
  LogOperationVO
} from "@/views/vitality/monitor/log-operation/utils/types";

/** 查询 */
export const getLogOperationPage = (params?: LogOperationVO) => {
  return http.get<Page, LogOperationVO>("/monitor/log-operation/page", {
    params
  });
};

/** 创建 */
export const createLogOperation = (data: FormItemProps) => {
  return http.post<R, FormItemProps>("/monitor/log-operation/create", { data });
};

/** 修改 */
export const updateLogOperation = (data: FormItemProps) => {
  return http.post<R, FormItemProps>("/monitor/log-operation/update", { data });
};

/** 删除 */
export const deleteLogOperation = (ids: String) => {
  return http.post<R, any>("/monitor/log-operation/delete/" + ids);
};

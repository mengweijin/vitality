import { http } from "@/utils/http";
import type {
  FormItemProps,
  ConfigVO
} from "@/views/vitality/system/config/utils/types";

/** 查询 */
export const getConfigPage = (params?: ConfigVO) => {
  return http.get<Page, ConfigVO>("/system/config/page", { params });
};

/** 创建 */
export const createConfig = (data: FormItemProps) => {
  return http.post<R, FormItemProps>("/system/config/create", { data });
};

/** 修改 */
export const updateConfig = (data: FormItemProps) => {
  return http.post<R, FormItemProps>("/system/config/update", { data });
};

/** 删除 */
export const deleteConfig = (ids: String) => {
  return http.post<R, any>("/system/config/delete/" + ids);
};

import { http } from "@/utils/http";
import type { FormItemProps } from "@/views/vitality/system/role/utils/types";

/** 查询 */
export const getRolePage = (data: any) => {
  return http.get<Page, any>("/system/role/page", { params: data });
};

export const getRoleMenu = () => {
  return http.get<any, any>("/system/role/list");
};

export const getRoleMenuIds = (data: any) => {
  return http.get<any, any>("/system/role/list", { data });
};

/** 创建 */
export const createRole = (data: FormItemProps) => {
  return http.post<any, any>("/system/role/create", { data });
};

/** 修改 */
export const updateRole = (data: FormItemProps) => {
  return http.post<any, any>("/system/role/update", { data });
};

/** 删除 */
export const deleteRole = (ids: String) => {
  return http.post<any, any>("/system/role/delete/" + ids);
};

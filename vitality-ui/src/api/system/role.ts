import { http } from "@/utils/http";
import type { FormItemProps } from "@/views/system/dept/utils/types";

/** 查询 */
export const getDeptList = () => {
  return http.get<any, any>("/system/dept/list");
};

/** 创建 */
export const createDept = (data: FormItemProps) => {
  return http.post<any, any>("/system/dept/create", { data });
};

/** 修改 */
export const updateDept = (data: FormItemProps) => {
  return http.post<any, any>("/system/dept/update", { data });
};

/** 删除 */
export const deleteDept = (ids: String) => {
  return http.post<any, any>("/system/dept/delete/" + ids);
};

export const getRoleList = (data: any) => {
  return http.get<any, any>("/system/dept/list", { data });
};

export const getRoleMenu = () => {
  return http.get<any, any>("/system/dept/list");
};

export const getRoleMenuIds = (data: any) => {
  return http.get<any, any>("/system/dept/list", { data });
};

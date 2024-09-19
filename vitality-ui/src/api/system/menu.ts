import { http } from "@/utils/http";
import type { FormItemProps } from "@/views/vitality/system/menu/utils/types";

/** 查询 */
export const getMenuList = () => {
  return http.get<any, any>("/system/menu/list");
};

/** 创建 */
export const createMenu = (data: FormItemProps) => {
  return http.post<any, any>("/system/menu/create", { data });
};

/** 修改 */
export const updateMenu = (data: FormItemProps) => {
  return http.post<any, any>("/system/menu/update", { data });
};

/** 删除 */
export const deleteMenu = (ids: String) => {
  return http.post<any, any>("/system/menu/delete/" + ids);
};

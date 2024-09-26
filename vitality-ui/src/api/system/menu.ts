import { http } from "@/utils/http";
import type {
  FormItemProps,
  MenuVO
} from "@/views/vitality/system/menu/utils/types";

/** 查询 */
export const getMenuList = (params?: MenuVO) => {
  return http.get<MenuVO[], MenuVO>("/system/menu/list", { params });
};

/** 查询 */
export const getMenuById = (id: String) => {
  return http.get<MenuVO, any>("/system/menu/" + id);
};

/** 创建 */
export const createMenu = (data: FormItemProps) => {
  return http.post<R, FormItemProps>("/system/menu/create", { data });
};

/** 修改 */
export const updateMenu = (data: FormItemProps) => {
  return http.post<R, FormItemProps>("/system/menu/update", { data });
};

/** 删除 */
export const deleteMenu = (ids: String) => {
  return http.post<R, any>("/system/menu/delete/" + ids);
};

import { http } from "@/utils/http";
import type {
  FormItemProps,
  CategoryVO
} from "@/views/vitality/system/category/utils/types";

/** 查询 */
export const getCategoryList = (params?: CategoryVO) => {
  return http.get<CategoryVO[], CategoryVO>("/system/category/list", {
    params
  });
};

/** 查询 */
export const getCategoryById = (id: String) => {
  return http.get<CategoryVO, any>("/system/category/" + id);
};

/** 创建 */
export const createCategory = (data: FormItemProps) => {
  return http.post<R, FormItemProps>("/system/category/create", { data });
};

/** 修改 */
export const updateCategory = (data: FormItemProps) => {
  return http.post<R, FormItemProps>("/system/category/update", { data });
};

/** 删除 */
export const deleteCategory = (ids: String) => {
  return http.post<R, any>("/system/category/delete/" + ids);
};

import { http } from "@/utils/http";
import type { FormItemProps, DeptVO } from "@/views/system/dept/utils/types";

/** 查询 */
export const getDeptList = (params?: DeptVO) => {
  return http.get<DeptVO[], DeptVO>("/system/dept/list", { params });
};

/** 查询 */
export const getDeptById = (id: String) => {
  return http.get<DeptVO, any>("/system/dept/" + id);
};

/** 创建 */
export const createDept = (data: FormItemProps) => {
  return http.post<R, FormItemProps>("/system/dept/create", { data });
};

/** 修改 */
export const updateDept = (data: FormItemProps) => {
  return http.post<R, FormItemProps>("/system/dept/update", { data });
};

/** 删除 */
export const deleteDept = (ids: String) => {
  return http.post<R, any>("/system/dept/delete/" + ids);
};

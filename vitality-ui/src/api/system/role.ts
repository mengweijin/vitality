import { http } from "@/utils/http";
import type {
  FormItemProps,
  RoleVO,
  RolePermissionBO
} from "@/views/vitality/system/role/utils/types";

/** 查询 */
export const getRolePage = (params?: RoleVO) => {
  return http.get<Page, RoleVO>("/system/role/page", { params });
};

/** 根据userId，获取对应角色id列表（userId：用户id） */
export const getRoleIdsByUserId = (userId?: string) => {
  return http.get<String[], String>(
    "/system/role/list-role-ids-by-user-id/" + userId
  );
};

/** 获取所有角色列表 */
export const getAllRoleList = () => {
  return http.get<RoleVO[], any>("/system/role/list");
};

/** 创建 */
export const createRole = (data: FormItemProps) => {
  return http.post<R, FormItemProps>("/system/role/create", { data });
};

/** 修改 */
export const updateRole = (data: FormItemProps) => {
  return http.post<R, FormItemProps>("/system/role/update", { data });
};

export const setRolePermission = (data: RolePermissionBO) => {
  return http.post<R, RolePermissionBO>("/system/role/set-permission", {
    data
  });
};

/** 删除 */
export const deleteRole = (ids: String) => {
  return http.post<R, any>("/system/role/delete/" + ids);
};

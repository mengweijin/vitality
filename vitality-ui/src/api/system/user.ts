import { http } from "@/utils/http";
import type {
  FormItemProps,
  UserVO,
  ChangePasswordBO,
  UserAvatarBO,
  RoleFormItemProps
} from "@/views/system/user/utils/types";

/** 查询 */
export const getUserPage = (params?: UserVO) => {
  return http.get<Page, UserVO>("/system/user/page", { params });
};

/** 查询 */
export const getSensitiveUserById = (id: string) => {
  return http.get<UserVO, string>("/system/user/sensitive/" + id);
};

/** 账户设置-个人信息 */
export const getMine = () => {
  return http.get<UserVO, any>("/system/user/mine");
};

/** 创建 */
export const createUser = (data: FormItemProps) => {
  return http.post<R, FormItemProps>("/system/user/create", { data });
};

/** 修改 */
export const updateUser = (data: FormItemProps) => {
  return http.post<R, FormItemProps>("/system/user/update", { data });
};

/** 重置密码 */
export const resetUserPassword = (data: ChangePasswordBO) => {
  return http.post<R, ChangePasswordBO>("/system/user/reset-password", {
    data
  });
};

/** 修改密码 */
export const changeUserPassword = (data: ChangePasswordBO) => {
  return http.post<R, ChangePasswordBO>("/system/user/change-password", {
    data
  });
};

/** 设置用户图像 */
export const setUserAvatar = (data: UserAvatarBO) => {
  return http.post<R, UserAvatarBO>("/system/user/set-avatar", {
    data
  });
};

/** 设置用户角色 */
export const setUserRoles = (data: RoleFormItemProps) => {
  return http.post<R, RoleFormItemProps>("/system/user/set-roles", {
    data
  });
};

/** 删除 */
export const deleteUser = (ids: String) => {
  return http.post<R, any>("/system/user/delete/" + ids);
};

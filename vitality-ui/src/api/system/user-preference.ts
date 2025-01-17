import { http } from "@/utils/http";

interface UserPreferenceVO extends BaseEntity {
  id?: string;
  userId?: string;
  userMessage?: string;
  systemMessage?: string;
  todoTask?: string;
}

/** 查询 */
export const getUserPreference = () => {
  return http.get<UserPreferenceVO, any>("/system/user-preference/query");
};

/** 保存 */
export const saveUserPreference = (data: UserPreferenceVO) => {
  return http.post<UserPreferenceVO, any>("/system/user-preference/save", {
    data
  });
};

export type { UserPreferenceVO };

import { http } from "@/utils/http";

export const getDeptList = () => {
  return http.request<any>("get", "/system/dept/list");
};

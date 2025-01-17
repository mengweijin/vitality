import { http } from "@/utils/http";
import type {
  SaSessionVO,
  TokenSignVO
} from "@/views/vitality/monitor/user-online/utils/types";

/** 查询 */
export const getUserOnlinePage = (params?: SaSessionVO) => {
  return http.get<Page, SaSessionVO>("/monitor/user-online/page", { params });
};

/** 查询 */
export const getUserOnlinePageTokenSignList = (sessionId: String) => {
  return http.post<any[], any>(
    "/monitor/user-online/token-sign-list/" + sessionId
  );
};

/** 下线 */
export const kickOutByUsername = (username: String) => {
  return http.post<R, any>(
    "/monitor/user-online/kick-out-by-username/" + username
  );
};

/** 下线 */
export const kickOutByToken = (data: TokenSignVO) => {
  return http.post<R, TokenSignVO>("/monitor/user-online/kick-out-by-token", {
    data
  });
};

import { http } from "@/utils/http";
// import type { FormProps } from "@/views/vitality/monitor/cache/utils/types";

/** 查询 */
export const getCacheNameList = () => {
  return http.get<string[], any>("/monitor/cache/names");
};

export const getCacheByName = (cacheName: string) => {
  return http.get<any[], any>("/monitor/cache/query?cacheName=" + cacheName);
};

export const deleteCacheByName = (cacheName: string, cacheKey: string) => {
  return http.post<R, any>(
    "/monitor/cache/remove?cacheName=" + cacheName + "&cacheKey=" + cacheKey
  );
};

import { http } from "@/utils/http";
import type {
  FormProps,
  NoticeVO
} from "@/views/vitality/system/notice/utils/types";

/** 查询 */
export const getNoticePage = (params?: NoticeVO) => {
  return http.get<Page, NoticeVO>("/system/notice/page", { params });
};

/** 创建 */
export const createNotice = (data: FormProps) => {
  return http.post<R, FormProps>("/system/notice/create", { data });
};

/** 修改 */
export const updateNotice = (data: FormProps) => {
  return http.post<R, FormProps>("/system/notice/update", { data });
};

/** 删除 */
export const deleteNotice = (ids: String) => {
  return http.post<R, any>("/system/notice/delete/" + ids);
};

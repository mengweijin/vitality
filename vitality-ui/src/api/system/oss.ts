import { http } from "@/utils/http";
import type {
  FormItemProps,
  OssVO
} from "@/views/vitality/system/oss/utils/types";

/** 查询 */
export const getOssPage = (params?: OssVO) => {
  return http.get<Page, OssVO>("/system/oss/page", { params });
};

/** 创建 */
export const createOss = (data: FormItemProps) => {
  return http.post<R, FormItemProps>("/system/oss/create", { data });
};

/** 修改 */
export const updateOss = (data: FormItemProps) => {
  return http.post<R, FormItemProps>("/system/oss/update", { data });
};

/** 删除 */
export const deleteOss = (ids: String) => {
  return http.post<R, any>("/system/oss/delete/" + ids);
};

/** 下载 */
export const download = (id: string) => {
  return http.get("/system/oss/download/" + id, null, {
    responseType: "blob",
    beforeResponseCallback: () => {}
  });
};

import { http } from "@/utils/http";
import type {
  FormItemProps,
  DictTypeVO
} from "@/views/vitality/system/dict-type/utils/types";

/** 查询 */
export const getDictTypePage = (params?: DictTypeVO) => {
  return http.get<Page, DictTypeVO>("/system/dict-type/page", { params });
};

/** 创建 */
export const createDictType = (data: FormItemProps) => {
  return http.post<R, FormItemProps>("/system/dict-type/create", { data });
};

/** 修改 */
export const updateDictType = (data: FormItemProps) => {
  return http.post<R, FormItemProps>("/system/dict-type/update", { data });
};

/** 删除 */
export const deleteDictType = (ids: String) => {
  return http.post<R, any>("/system/dict-type/delete/" + ids);
};

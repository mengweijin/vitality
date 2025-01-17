import { http } from "@/utils/http";
import type {
  FormItemProps,
  DictDataVO
} from "@/views/vitality/system/dict-data/utils/types";

/** 查询 */
export const getDictDataPage = (params?: DictDataVO) => {
  return http.get<Page, DictDataVO>("/system/dict-data/page", { params });
};

/** 创建 */
export const createDictData = (data: FormItemProps) => {
  return http.post<R, FormItemProps>("/system/dict-data/create", { data });
};

/** 修改 */
export const updateDictData = (data: FormItemProps) => {
  return http.post<R, FormItemProps>("/system/dict-data/update", { data });
};

/** 删除 */
export const deleteDictData = (ids: String) => {
  return http.post<R, any>("/system/dict-data/delete/" + ids);
};

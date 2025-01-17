import { http } from "@/utils/http";
import type { DictDataVO } from "@/views/vitality/system/dict-data/utils/types";

export const getDictDataList = () => {
  return http.get<DictDataVO[], any>("/system/dict-data/list");
};

/** 根据字典类型编码获取字典数据 */
export const getDictDataListByCode = (code: string) => {
  return http.get<DictDataVO[], String>(
    "/system/dict-data/get-by-code/" + code
  );
};

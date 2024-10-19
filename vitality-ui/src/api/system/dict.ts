import { http } from "@/utils/http";

export const getDictDataList = () => {
  return http.get<DictDataVO[], any>("/system/dict-data/list");
};

/** 根据字典类型编码获取字典数据 */
export const getDictDataListByCode = (code: string) => {
  return http.get<DictDataVO[], String>(
    "/system/dict-data/get-by-code/" + code
  );
};

interface DictDataVO {
  id?: string;
  /** 字典编码 */
  code?: string;
  /** 字典标签 */
  label?: string;
  /** 字典值 */
  val?: string;
  /** 字典排序 */
  seq?: number;
  /** 是否禁用。[Y, N] */
  disabled?: string;
  /** 备注 */
  remark?: string;
}

export type { DictDataVO };

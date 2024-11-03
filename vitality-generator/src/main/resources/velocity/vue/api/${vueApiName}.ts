import { http } from "@/utils/http";
import type {
  FormItemProps,
  ${entityName}VO
} from "@/views/vitality/system/${vueApiName}/utils/types";

/** 查询 */
export const get${entityName}Page = (params?: ${entityName}VO) => {
  return http.get<Page, ${entityName}VO>("${requestMapping}/page", { params });
};

/** 创建 */
export const create${entityName} = (data: FormItemProps) => {
  return http.post<R, FormItemProps>("${requestMapping}/create", { data });
};

/** 修改 */
export const update${entityName} = (data: FormItemProps) => {
  return http.post<R, FormItemProps>("${requestMapping}/update", { data });
};

/** 删除 */
export const delete${entityName} = (ids: String) => {
  return http.post<R, any>("${requestMapping}/delete/" + ids);
};

import { http } from "@/utils/http";
import type {
  GeneratorArgsBO,
  TableInfoVO
} from "@/views/vitality/tool/generator/utils/types";

/** 查询 */
export const getTableList = (params: TableInfoVO) => {
  return http.get<TableInfoVO[], TableInfoVO>("/generator/table/list", {
    params
  });
};

/** 查询 */
export const getTemplateList = () => {
  return http.get<any[], any>("/generator/template/list");
};

/** 查询 */
export const getDefaultArgs = () => {
  return http.get<GeneratorArgsBO, any>("/generator/args/default");
};

export const generateCode = (data: GeneratorArgsBO) => {
  return http.request<R>(
    "post",
    "/generator/execute",
    { data },
    {
      beforeResponseCallback: () => {}
    }
  );
};

export const downloadCode = (data: GeneratorArgsBO) => {
  return http.post(
    "/generator/download",
    { data: data },
    {
      responseType: "blob",
      beforeResponseCallback: () => {}
    }
  );
};

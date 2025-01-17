interface FormItemProps {
  id?: string;
  /** 名称 */
  title?: string;
  /** 操作类型 */
  operationType?: string;
  /** http 请求方式 */
  httpMethod?: string;
  /** 请求url */
  url?: string;
  /** 请求方法名称 */
  methodName?: string;
  /** 请求数据 */
  requestData?: string;
  /** 响应数据 */
  responseData?: string;
  /** 执行消耗时间 */
  costTime?: string;
  /** 操作是否成功 */
  success?: string;
  /** 失败信息 */
  errorMsg?: string;
}
interface FormProps {
  row: FormItemProps;
}

interface DetailVO {
  formInline: LogOperationVO;
}

interface LogOperationVO extends FormItemProps, BaseEntity, Page {}

export type { FormItemProps, FormProps, LogOperationVO, DetailVO };

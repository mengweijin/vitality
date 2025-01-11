interface FormProps {
  id?: string;
  /** SaSession 的 类型 */
  type?: string;
  /** 登录类型 */
  loginType?: string;
  /** 所属 loginId （当此 SaSession 属于 Account-Session 时，此值有效） */
  loginId?: string;
  /** 所属 Token （当此 SaSession 属于 Token-Session 时，此值有效） */
  token?: string;
  /** 此 SaSession 的创建时间（13位时间戳） */
  createTime?: number;
  /** 列表查询关键词参数 */
  keyword?: string;
}

interface TokenSignVO {
  value?: string;
  token?: string;
  device?: string;
  tag?: string;
}

interface SaSessionVO extends FormProps, Page {
  tokenSignList?: Array<TokenSignVO>;
}

interface Props {
  data: SaSessionVO;
}

export type { FormProps, Props, SaSessionVO, TokenSignVO };

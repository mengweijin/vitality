interface FormProps {
  id?: string;
  /** 登录账号 */
  username?: string;
  /** 登录类型 */
  loginType?: string;
  /** IP */
  ip?: string;
  /** IP 位置 */
  ipLocation?: string;
  /** 浏览器 */
  browser?: string;
  /** 设备平台类型 */
  platform?: string;
  /** 操作系统 */
  os?: string;
  /** 登录是否成功。[Y, N] */
  success?: string;
  /** 失败信息 */
  errorMsg?: string;
}

interface LogLoginVO extends FormProps, BaseEntity, Page {}

interface Props {
  data: LogLoginVO;
}

export type { FormProps, Props, LogLoginVO };

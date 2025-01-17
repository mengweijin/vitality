interface FormProps {
  id?: string;
  /** 日志级别 */
  logLevel?: string;
  /** 日志信息 */
  message?: string;
  /** 类名称 */
  className?: string;
  /** 方法名称 */
  methodName?: string;
  /** 异常名称 */
  exceptionName?: string;
  /** 堆栈信息 */
  stackTrace?: string;
}

interface LogAlertVO extends FormProps, BaseEntity, Page {}

interface Props {
  data: LogAlertVO;
}

export type { FormProps, Props, LogAlertVO };

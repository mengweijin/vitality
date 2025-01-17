interface FormItemProps {
  id?: string;
  /** 名称 */
  name?: string;
  /** 后缀 */
  suffix?: string;
  /** 存储位置 */
  storagePath?: string;
  /** MD5 码 */
  md5?: string;
}
interface FormProps {
  formInline: FormItemProps;
}

interface OssVO extends FormItemProps, BaseEntity, Page {}

export type { FormItemProps, FormProps, OssVO };

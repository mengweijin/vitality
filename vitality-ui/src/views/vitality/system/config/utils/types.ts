interface FormItemProps {
  id?: string;
  /** 名称 */
  name?: string;
  /** 编码 */
  code?: string;
  /** 值 */
  val?: string;
  /** 备注 */
  remark?: string;
}
interface FormProps {
  formInline: FormItemProps;
}

interface ConfigVO extends FormItemProps, BaseEntity, Page {}

export type { FormItemProps, FormProps, ConfigVO };

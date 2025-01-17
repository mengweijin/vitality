interface FormItemProps {
  id?: string;
  /** 编码 */
  code?: string;
  /** 值 */
  val?: string;
  /** 名称 */
  label?: string;
  /** 字典标签样式 */
  tagStyle?: string;
  /** 排序 */
  seq?: number;
  /** 是否禁用。[Y, N] */
  disabled?: string;
  /** 备注 */
  remark?: string;
}
interface FormProps {
  formInline: FormItemProps;
}

interface DictDataVO extends FormItemProps, BaseEntity, Page {}

export type { FormItemProps, FormProps, DictDataVO };

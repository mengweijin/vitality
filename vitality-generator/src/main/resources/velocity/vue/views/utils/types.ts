interface FormItemProps {
  id?: string;
  /** 名称 */
  name?: string;
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

interface ${entityName}VO extends FormItemProps, BaseEntity, Page {}

export type { FormItemProps, FormProps, ${entityName}VO };

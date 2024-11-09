interface FormProps {
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

interface ${entityName}VO extends FormProps, BaseEntity, Page {}

interface Props {
  data: ${entityName}VO;
}

export type { FormProps, Props, ${entityName}VO };

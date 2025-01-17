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

interface UserVO extends FormProps, BaseEntity, Page {
  deptId?: string;
}

interface Props {
  data: UserVO;
  higherOptions?: Record<string, unknown>[];
}

export type { FormProps, Props, UserVO };

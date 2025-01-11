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

interface PostVO extends FormProps, BaseEntity, Page {}

interface Props {
  data: PostVO;
}

export type { FormProps, Props, PostVO };

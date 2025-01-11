interface FormProps {
  id?: string;
  /** 名称 */
  name?: string;
  /** 备注 */
  description?: string;
  /** 是否已发布。[Y, N] */
  released?: string;
}

interface NoticeVO extends FormProps, BaseEntity, Page {}

interface Props {
  data: NoticeVO;
}

export type { FormProps, Props, NoticeVO };

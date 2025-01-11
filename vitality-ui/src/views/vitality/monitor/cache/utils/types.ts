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

interface CacheVO extends FormProps, BaseEntity, Page {
  deptId?: string;
  cacheName?: string;
}

interface Props {
  data: CacheVO;
  higherOptions?: Record<string, unknown>[];
}

export type { FormProps, Props, CacheVO };

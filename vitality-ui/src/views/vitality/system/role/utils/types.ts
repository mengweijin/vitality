// 虽然字段很少 但是抽离出来 后续有扩展字段需求就很方便了

interface FormItemProps {
  id: string;
  /** 角色名称 */
  name: string;
  /** 角色编号 */
  code: string;
  /** 排序 */
  seq: number;
  /** 是否禁用。[Y, N] */
  disabled: string;
  /** 备注 */
  remark: string;
}
interface FormProps {
  formInline: FormItemProps;
}

interface RoleVO extends BaseEntity, Page {
  /** 角色名称 */
  name?: string;
  /** 角色编号 */
  code?: string;
  /** 排序 */
  seq?: number;
  /** 是否禁用。[Y, N] */
  disabled?: string;
  /** 备注 */
  remark?: string;
}

export type { FormItemProps, FormProps, RoleVO };

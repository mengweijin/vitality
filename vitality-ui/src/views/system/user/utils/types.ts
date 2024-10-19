interface FormItemProps {
  id?: string;
  deptId?: string;
  title?: string;
  parentId?: number;
  nickname?: string;
  username?: string;
  password?: string;
  mobile?: string;
  email?: string;
  gender?: string;
  disabled?: string;
  remark?: string;
}
interface FormProps {
  formInline: FormItemProps;
  higherDeptOptions?: Record<string, unknown>[];
}

interface UserVO extends FormItemProps, BaseEntity, Page {
  deptName?: string;
  avatar?: string;
}

interface ChangePasswordBO {
  username?: string;
  password?: string;
  newPassword?: string;
}

interface UserAvatarBO {
  userId?: string;
  avatar?: string;
}

interface RoleFormItemProps {
  userId?: string;
  nickname?: string;
  /** 选中的角色列表 */
  roleIds?: Record<string, unknown>[];
}

interface RoleFormProps {
  formInline: RoleFormItemProps;
  /** 角色列表 */
  roleOptions: any[];
}

export type {
  FormItemProps,
  FormProps,
  RoleFormItemProps,
  RoleFormProps,
  UserVO,
  ChangePasswordBO,
  UserAvatarBO
};

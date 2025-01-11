interface FormItemProps {
  id?: string;
  parentId?: string;
  name?: string;
  seq?: number;
  disabled?: string;
  remark?: string;
}
interface FormProps {
  formInline: FormItemProps;
  higherDeptOptions: Record<string, unknown>[];
}

interface DeptVO extends FormItemProps, BaseEntity, Page {}

export type { FormItemProps, FormProps, DeptVO };

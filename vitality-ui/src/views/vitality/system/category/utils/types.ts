interface FormItemProps {
  id?: string;
  parentId?: string;
  code?: string;
  name?: string;
  seq?: number;
  disabled?: string;
  remark?: string;
}
interface FormProps {
  formInline: FormItemProps;
  higherDeptOptions: Record<string, unknown>[];
}

interface CategoryVO extends FormItemProps, BaseEntity, Page {}

export type { FormItemProps, FormProps, CategoryVO };

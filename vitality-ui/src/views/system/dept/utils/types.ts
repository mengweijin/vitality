interface FormItemProps {
  higherDeptOptions: Record<string, unknown>[];
  id: string;
  parentId: string;
  name: string;
  seq: number;
  disabled: string;
  remark: string;
}
interface FormProps {
  formInline: FormItemProps;
}

export type { FormItemProps, FormProps };

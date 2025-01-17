interface TableInfoVO {
  name?: string;
  havePrimaryKey?: boolean;
  fieldNames?: string;
  comment?: string;
}

interface GeneratorArgsBO {
  templateId?: string;
  templateName?: string;
  templateContent?: string;
  tableName?: string;
  tablePrefix?: string;
  packages?: string;
  module?: string;
  author?: string;
}

interface FormProps {
  formInline: TableInfoVO;
}

export type { TableInfoVO, FormProps, GeneratorArgsBO };

import dayjs from "dayjs";
import ReDictTag from "@/components/ReDictTag";

export function useTableColumns() {
  const tableColumns: TableColumnList = [
    {
      label: "勾选列", // 如果需要表格多选，此处label必须设置
      type: "selection",
      fixed: "left",
      reserveSelection: true // 数据刷新后保留选项
    },
    {
      label: "编号",
      prop: "id",
      minWidth: 180,
      align: "left",
      hide: true
    },
    {
      label: "日志标题",
      prop: "title",
      minWidth: 180,
      hide: true
    },
    {
      label: "操作类型",
      prop: "operationType",
      minWidth: 100,
      cellRenderer: ({ row, props }) => (
        <ReDictTag
          size={props.size}
          code="vtl_operation_log_type"
          value={row.operationType}
        ></ReDictTag>
      )
    },
    {
      label: "请求URL",
      prop: "url",
      minWidth: 180
    },
    {
      label: "请求方法名称",
      prop: "methodName",
      minWidth: 180,
      hide: false
    },
    {
      label: "HTTP方式",
      prop: "httpMethod",
      minWidth: 100,
      cellRenderer: ({ row, props }) => (
        <ReDictTag
          size={props.size}
          code="vtl_http_request_type"
          value={row.httpMethod}
        ></ReDictTag>
      )
    },
    {
      label: "请求数据",
      prop: "requestData",
      minWidth: 180,
      hide: true
    },
    {
      label: "响应数据",
      prop: "responseData",
      minWidth: 180,
      hide: true
    },
    {
      label: "执行时间(ms)",
      prop: "costTime",
      minWidth: 100
    },
    {
      label: "是否成功",
      prop: "success",
      minWidth: 100,
      cellRenderer: ({ row, props }) => (
        <ReDictTag
          size={props.size}
          code="vtl_succeeded"
          value={row.success}
        ></ReDictTag>
      )
    },
    {
      label: "失败信息",
      prop: "errorMsg",
      minWidth: 180,
      hide: true
    },
    {
      label: "操作者",
      minWidth: 100,
      prop: "createByName",
      hide: false
    },
    {
      label: "操作时间",
      minWidth: 160,
      prop: "createTime",
      formatter: ({ createTime }) =>
        dayjs(createTime).format("YYYY-MM-DD HH:mm:ss"),
      hide: false
    },
    {
      label: "更新者",
      minWidth: 100,
      prop: "updateByName",
      hide: true
    },
    {
      label: "更新时间",
      minWidth: 160,
      prop: "updateTime",
      formatter: ({ updateTime }) =>
        dayjs(updateTime).format("YYYY-MM-DD HH:mm:ss"),
      hide: true
    },
    {
      label: "操作",
      fixed: "right",
      width: 160,
      slot: "operation"
    }
  ];

  return {
    tableColumns
  };
}

import ReDictTag from "@/components/ReDictTag";

export function useDetailColumns() {
  const detailColumns = [
    {
      label: "日志 ID",
      prop: "id",
      copy: true,
      // width: 180,
      hide: () => false
    },
    {
      label: "日志标题",
      prop: "title",
      copy: true
    },
    {
      label: "操作类型",
      prop: "operationType",
      cellRenderer: ({ value }) => {
        return (
          <ReDictTag
            code="vtl_operation_log_type"
            size="small"
            value={value}
          ></ReDictTag>
        );
      }
    },
    {
      label: "HTTP 请求方式",
      prop: "httpMethod",
      cellRenderer: ({ value }) => {
        return (
          <ReDictTag
            code="vtl_http_request_type"
            size="small"
            value={value}
          ></ReDictTag>
        );
      }
    },
    {
      label: "请求 URL",
      prop: "url",
      copy: true
    },
    {
      label: "请求方法名称",
      prop: "methodName",
      copy: true
    },
    {
      label: "执行时间（ms）",
      prop: "costTime",
      labelRenderer: () => {
        return (
          <div style={{ display: "flex", alignItems: "center" }}>
            <span style={{ color: "rgb(122, 114, 255)", marginLeft: "2px" }}>
              执行时间
            </span>
          </div>
        );
      },
      cellRenderer: ({ value }) => {
        return <span>{value} 毫秒</span>;
      }
    },
    {
      label: "是否成功",
      prop: "success",
      cellRenderer: ({ value }) => {
        return (
          <ReDictTag
            code="vtl_succeeded"
            size="small"
            value={value}
          ></ReDictTag>
        );
      }
    },
    {
      label: "操作用户",
      prop: "createByName"
    },
    {
      label: "操作时间",
      prop: "createTime"
    }
  ];

  return {
    detailColumns
  };
}

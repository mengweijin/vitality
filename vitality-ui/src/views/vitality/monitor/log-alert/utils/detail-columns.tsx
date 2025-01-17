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
      label: "日志级别",
      prop: "logLevel",
      cellRenderer: ({ value }) => {
        return (
          <ReDictTag
            code="vtl_log_level"
            size="small"
            value={value}
          ></ReDictTag>
        );
      }
    },
    {
      label: "日志信息",
      prop: "message",
      copy: true
    },
    {
      label: "异常名称",
      prop: "exceptionName",
      copy: true,
      labelRenderer: () => {
        return (
          <div style={{ display: "flex", alignItems: "center" }}>
            <span style={{ color: "rgb(122, 114, 255)", marginLeft: "2px" }}>
              异常名称
            </span>
          </div>
        );
      }
    },
    {
      label: "类名称",
      prop: "className",
      copy: true
    },
    {
      label: "方法名称",
      prop: "methodName",
      copy: true
    },

    {
      label: "触发用户",
      prop: "createByName"
    },
    {
      label: "发生时间",
      prop: "createTime"
    },
    {
      label: "更新者",
      prop: "updateByName",
      hide: () => true
    },
    {
      label: "更新时间",
      prop: "updateTime",
      hide: () => true
    }
  ];

  return {
    detailColumns
  };
}

import ReDictTag from "@/components/ReDictTag";

export function useDetailColumns() {
  const detailColumns = [
    {
      label: "ID",
      prop: "id",
      copy: true,
      // width: 180,
      hide: () => false
    },
    {
      label: "名称",
      prop: "name",
      copy: true,
      labelRenderer: () => {
        return (
          <div style={{ display: "flex", alignItems: "center" }}>
            <span style={{ color: "rgb(122, 114, 255)", marginLeft: "2px" }}>
              名称
            </span>
          </div>
        );
      },
      cellRenderer: ({ value }) => {
        return <span>{value}</span>;
      }
    },
    {
      label: "排序",
      prop: "seq"
    },
    {
      label: "状态",
      prop: "disabled",
      cellRenderer: ({ value }) => {
        return (
          <ReDictTag code="vtl_disabled" size="small" value={value}></ReDictTag>
        );
      }
    },
    {
      label: "创建者",
      prop: "createByName",
      hide: () => false
    },
    {
      label: "创建时间",
      prop: "createTime",
      hide: () => false
    },
    {
      label: "更新者",
      prop: "updateByName",
      hide: () => false
    },
    {
      label: "更新时间",
      prop: "updateTime",
      hide: () => false
    }
  ];

  return {
    detailColumns
  };
}

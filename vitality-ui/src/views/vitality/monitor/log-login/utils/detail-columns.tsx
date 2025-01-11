import ReDictTag from "@/components/ReDictTag";

export function useDetailColumns() {
  const detailColumns = [
    {
      label: "ID",
      prop: "id",
      copy: true,
      // width: 180,
      hide: () => true
    },
    {
      label: "登录账号",
      prop: "username",
      copy: true,
      labelRenderer: () => {
        return (
          <div style={{ display: "flex", alignItems: "center" }}>
            <span style={{ color: "rgb(122, 114, 255)", marginLeft: "2px" }}>
              登录账号
            </span>
          </div>
        );
      }
    },
    {
      label: "登录类型",
      prop: "loginType",
      cellRenderer: ({ value }) => {
        return (
          <ReDictTag
            code="vtl_login_type"
            size="small"
            value={value}
          ></ReDictTag>
        );
      }
    },
    {
      label: "IP",
      prop: "ip",
      copy: true
    },
    {
      label: "登录位置",
      prop: "ipLocation",
      copy: true
    },
    {
      label: "浏览器",
      prop: "browser",
      copy: true
    },
    {
      label: "设备平台",
      prop: "platform",
      copy: true
    },
    {
      label: "操作系统",
      prop: "os",
      copy: true
    },
    {
      label: "登录状态",
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
      label: "创建者",
      prop: "createByName",
      hide: () => true
    },
    {
      label: "登录时间",
      prop: "createTime",
      hide: () => false
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

import ReDictTag from "@/components/ReDictTag";
import dayjs from "dayjs";

export function useSecurityLogColumns() {
  const securityLogColumns = [
    {
      label: "编号",
      prop: "id",
      minWidth: 180,
      align: "left",
      hide: true
    },
    {
      label: "登录账号",
      prop: "username",
      minWidth: 80,
      hide: true
    },
    {
      label: "登录类型",
      prop: "loginType",
      minWidth: 80,
      cellRenderer: scope => (
        <ReDictTag
          code="vtl_login_type"
          size="small"
          value={scope.row.loginType}
        ></ReDictTag>
      )
    },
    {
      label: "IP",
      prop: "ip",
      minWidth: 120
    },
    {
      label: "登录位置",
      prop: "ipLocation",
      minWidth: 160
    },
    {
      label: "浏览器",
      prop: "browser",
      minWidth: 100
    },
    {
      label: "设备平台",
      prop: "platform",
      minWidth: 100
    },
    {
      label: "操作系统",
      prop: "os",
      minWidth: 260
    },
    {
      label: "状态",
      prop: "success",
      minWidth: 80,
      cellRenderer: scope => (
        <ReDictTag
          code="vtl_succeeded"
          size="small"
          value={scope.row.success}
        ></ReDictTag>
      )
    },
    {
      label: "失败信息",
      prop: "errorMsg",
      minWidth: 160,
      hide: true
    },
    {
      label: "操作者",
      minWidth: 100,
      prop: "createByName",
      hide: true
    },
    {
      label: "登录时间",
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
    }
  ];

  return {
    securityLogColumns
  };
}

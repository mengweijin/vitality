import dayjs from "dayjs";

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
      label: "会话类型",
      prop: "type",
      copy: true,
      labelRenderer: () => {
        return (
          <div style={{ display: "flex", alignItems: "center" }}>
            <span style={{ color: "rgb(122, 114, 255)", marginLeft: "2px" }}>
              会话类型
            </span>
          </div>
        );
      }
    },
    {
      label: "登录类型",
      prop: "loginType"
    },
    {
      label: "登录账号",
      prop: "loginId",
      copy: true
    },
    {
      label: "Token",
      prop: "token",
      copy: true,
      hide: () => true
    },
    {
      label: "登录时间",
      prop: "createTime",
      copy: true,
      cellRenderer: ({ value }) => {
        return dayjs(value).format("YYYY-MM-DD HH:mm:ss");
      }
    }
  ];

  return {
    detailColumns
  };
}

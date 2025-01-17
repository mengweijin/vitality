import { reactive, ref, onMounted, h, toRaw, type Ref } from "vue";
import dayjs from "dayjs";
import { deviceDetection, getKeyList } from "@pureadmin/utils";
import { addDialog } from "@/components/ReDialog";
import EditPage from "../edit.vue";
import DetailPage from "../detail.vue";
import type { FormProps, LogLoginVO } from "../utils/types";
import type { PaginationProps } from "@pureadmin/table";

import {
  getLogLoginPage,
  createLogLogin,
  updateLogLogin,
  deleteLogLogin
} from "@/api/monitor/log-login";
import ReDictTag from "@/components/ReDictTag";

export function useLogLogin(tableRef: Ref) {
  const form = reactive<LogLoginVO>({});
  const formRef = ref();
  const dataList = ref([]);
  const selectedNum = ref(0);
  const curRow = ref();
  const loading = ref(true);

  const pagination = reactive<PaginationProps>({
    total: 0,
    pageSize: 10,
    currentPage: 1,
    background: true,
    pageSizes: [10, 20, 30, 50]
  });

  const columns: TableColumnList = [
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
      label: "登录账号",
      prop: "username",
      minWidth: 80
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

  function handleDelete(row) {
    deleteLogLogin(row.id).then(() => {
      onSearch();
    });
  }

  /** 批量删除 */
  function handleBatchDelete() {
    // 返回当前选中的行
    const curSelected = tableRef.value.getTableRef().getSelectionRows();
    const ids = getKeyList(curSelected, "id").join();
    deleteLogLogin(ids).then(() => {
      tableRef.value.getTableRef().clearSelection();
      onSearch();
    });
  }

  /** 当CheckBox选择项发生变化时会触发该事件 */
  function handleSelectionChange(val) {
    selectedNum.value = val.length;
    // 重置表格高度
    tableRef.value.setAdaptive();
  }

  /** 取消选择 */
  function handleSelectionCancel() {
    selectedNum.value = 0;
    // 用于多选表格，清空用户的选择
    tableRef.value.getTableRef().clearSelection();
  }

  function handleSizeChange(val: number) {
    pagination.pageSize = val;
    onSearch();
  }

  function handleCurrentChange(val: number) {
    pagination.currentPage = val;
    onSearch();
  }

  async function onSearch() {
    loading.value = true;
    form.current = pagination.currentPage;
    form.size = pagination.pageSize;
    const page = await getLogLoginPage(toRaw(form));
    dataList.value = page.records;
    pagination.total = page.total;
    pagination.pageSize = page.size;
    pagination.currentPage = page.current;
    setTimeout(() => {
      loading.value = false;
    }, 500);
  }

  const resetForm = formEl => {
    if (!formEl) return;
    formEl.resetFields();
    onSearch();
  };

  function openEditDialog(title = "新增", row?: LogLoginVO) {
    addDialog({
      title: `${title}`,
      props: {
        data: {
          id: row?.id ?? null,
          username: row?.username ?? "",
          loginType: row?.loginType ?? "",
          ip: row?.ip ?? "",
          ipLocation: row?.ipLocation ?? "",
          browser: row?.browser ?? "",
          platform: row?.platform ?? "",
          os: row?.os ?? "",
          success: row?.success ?? "",
          errorMsg: row?.errorMsg ?? ""
        }
      },
      width: "40%",
      draggable: true,
      fullscreen: deviceDetection(),
      fullscreenIcon: true,
      closeOnClickModal: false,
      contentRenderer: () => h(EditPage, { ref: formRef, data: null }),
      beforeSure: (done, { options }) => {
        const curData = options.props.data as FormProps;
        function chores() {
          done(); // 关闭弹框
          onSearch(); // 刷新表格数据
        }
        formRef.value.getRef().validate(valid => {
          if (valid) {
            // 表单规则校验通过
            if (curData.id) {
              updateLogLogin(curData).then(() => {
                chores();
              });
            } else {
              createLogLogin(curData).then(() => {
                chores();
              });
            }
          }
        });
      }
    });
  }

  function openDetailDialog(title = "详情", row?: LogLoginVO) {
    addDialog({
      title: `${title}`,
      props: {
        data: row
      },
      width: "80%",
      draggable: true,
      fullscreen: deviceDetection(),
      fullscreenIcon: true,
      closeOnClickModal: false,
      hideFooter: true,
      contentRenderer: () => h(DetailPage, { data: null })
    });
  }

  onMounted(async () => {
    onSearch();
  });

  return {
    form,
    curRow,
    loading,
    columns,
    dataList,
    selectedNum,
    pagination,
    onSearch,
    resetForm,
    openEditDialog,
    openDetailDialog,
    handleDelete,
    handleBatchDelete,
    handleSelectionCancel,
    handleSizeChange,
    handleCurrentChange,
    handleSelectionChange
  };
}

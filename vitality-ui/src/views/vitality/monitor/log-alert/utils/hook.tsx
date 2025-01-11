import { reactive, ref, onMounted, h, toRaw, type Ref } from "vue";
import dayjs from "dayjs";
import { deviceDetection, getKeyList } from "@pureadmin/utils";
import { addDialog } from "@/components/ReDialog";
import EditPage from "../edit.vue";
import DetailPage from "../detail.vue";
import type { FormProps, LogAlertVO } from "./types";
import type { PaginationProps } from "@pureadmin/table";

import {
  getLogAlertPage,
  createLogAlert,
  updateLogAlert,
  deleteLogAlert
} from "@/api/monitor/log-alert";
import ReDictTag from "@/components/ReDictTag";

export function useLogAlert(tableRef: Ref) {
  const form = reactive<LogAlertVO>({});
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
      label: "日志级别",
      prop: "logLevel",
      minWidth: 90,
      cellRenderer: scope => (
        <ReDictTag
          code="vtl_log_level"
          size="small"
          value={scope.row.logLevel}
        ></ReDictTag>
      )
    },
    {
      label: "日志信息",
      prop: "message",
      minWidth: 280
    },
    {
      label: "异常名称",
      prop: "exceptionName",
      minWidth: 180
    },
    {
      label: "类名称",
      prop: "className",
      minWidth: 180
    },
    {
      label: "方法名称",
      prop: "methodName",
      minWidth: 180
    },
    {
      label: "堆栈信息",
      prop: "stackTrace",
      minWidth: 180,
      hide: true
    },
    {
      label: "触发用户",
      minWidth: 100,
      prop: "createByName",
      hide: false
    },
    {
      label: "发生时间",
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
      width: 200,
      slot: "operation"
    }
  ];

  function handleDelete(row) {
    deleteLogAlert(row.id).then(() => {
      onSearch();
    });
  }

  /** 批量删除 */
  function handleBatchDelete() {
    // 返回当前选中的行
    const curSelected = tableRef.value.getTableRef().getSelectionRows();
    const ids = getKeyList(curSelected, "id").join();
    deleteLogAlert(ids).then(() => {
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
    const page = await getLogAlertPage(toRaw(form));
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

  function openEditDialog(title = "新增", row?: LogAlertVO) {
    addDialog({
      title: `${title}`,
      props: {
        data: {
          id: row?.id ?? null
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
              updateLogAlert(curData).then(() => {
                chores();
              });
            } else {
              createLogAlert(curData).then(() => {
                chores();
              });
            }
          }
        });
      }
    });
  }

  function openDetailDialog(title = "详情", row?: LogAlertVO) {
    addDialog({
      title: `${title}`,
      props: {
        data: row
      },
      width: "80%",
      draggable: true,
      fullscreen: true,
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

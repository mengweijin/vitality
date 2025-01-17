import { reactive, ref, onMounted, h, toRaw, type Ref } from "vue";
import dayjs from "dayjs";
import { deviceDetection, getKeyList } from "@pureadmin/utils";
import { addDialog } from "@/components/ReDialog";
import EditPage from "../edit.vue";
import DetailPage from "../detail.vue";
import type { SaSessionVO } from "../utils/types";
import type { PaginationProps } from "@pureadmin/table";

import {
  getUserOnlinePage,
  kickOutByUsername
} from "@/api/monitor/user-online";

export function useLogLogin(tableRef: Ref) {
  const form = reactive<SaSessionVO>({});
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
      reserveSelection: true, // 数据刷新后保留选项
      hide: true
    },
    {
      label: "ID",
      prop: "id",
      minWidth: 220,
      align: "left",
      hide: false
    },
    {
      label: "会话类型",
      prop: "type",
      minWidth: 140
    },
    {
      label: "登录类型",
      prop: "loginType",
      minWidth: 80
    },
    {
      label: "账号",
      prop: "loginId",
      minWidth: 100
    },
    {
      label: "Token",
      prop: "token",
      minWidth: 180,
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
      label: "操作",
      fixed: "right",
      width: 160,
      slot: "operation"
    }
  ];

  function handleDelete(row) {
    kickOutByUsername(row.loginId).then(() => {
      onSearch();
    });
  }

  /** 批量删除 */
  function handleBatchDelete() {
    // 返回当前选中的行
    const curSelected = tableRef.value.getTableRef().getSelectionRows();
    const ids = getKeyList(curSelected, "loginId").join();
    kickOutByUsername(ids).then(() => {
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
    const page = await getUserOnlinePage(toRaw(form));
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

  function openEditDialog(title = "新增", row?: SaSessionVO) {
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
      contentRenderer: () => h(EditPage, { ref: formRef, data: null })
    });
  }

  function openDetailDialog(title = "详情", row?: SaSessionVO) {
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

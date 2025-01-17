import { reactive, ref, onMounted, h, toRaw, type Ref } from "vue";
import dayjs from "dayjs";
import { deviceDetection, getKeyList } from "@pureadmin/utils";
import { addDialog } from "@/components/ReDialog";
import EditPage from "../edit.vue";
import DetailPage from "../detail.vue";
import type { FormProps, NoticeVO } from "../utils/types";
import type { PaginationProps } from "@pureadmin/table";
import ReDictTag from "@/components/ReDictTag";

import {
  getNoticePage,
  createNotice,
  updateNotice,
  deleteNotice
} from "@/api/system/notice";

export function useNotice(tableRef: Ref) {
  const form = reactive<NoticeVO>({});
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
      label: "编号",
      prop: "id",
      minWidth: 180,
      align: "left",
      hide: true
    },
    {
      label: "标题",
      prop: "name",
      minWidth: 180
    },
    {
      label: "发布状态",
      prop: "released",
      width: 90,
      cellRenderer: scope => (
        <ReDictTag
          code="vtl_released"
          size="small"
          value={scope.row.released}
        ></ReDictTag>
      )
    },
    // {
    //   label: "内容",
    //   prop: "description",
    //   minWidth: 160,
    //   hide: true
    // },
    {
      label: "创建者",
      width: 100,
      prop: "createByName",
      hide: false
    },
    {
      label: "创建时间",
      width: 160,
      prop: "createTime",
      formatter: ({ createTime }) =>
        dayjs(createTime).format("YYYY-MM-DD HH:mm:ss"),
      hide: false
    },
    {
      label: "更新者",
      width: 100,
      prop: "updateByName",
      hide: false
    },
    {
      label: "最后更新时间",
      width: 160,
      prop: "updateTime",
      formatter: ({ updateTime }) =>
        dayjs(updateTime).format("YYYY-MM-DD HH:mm:ss"),
      hide: false
    },
    {
      label: "操作",
      fixed: "right",
      width: 280,
      slot: "operation"
    }
  ];

  function handleDelete(row) {
    deleteNotice(row.id).then(() => {
      onSearch();
    });
  }

  function handleRelease(row: FormProps) {
    updateNotice({ id: row.id, released: "Y" }).then(() => {
      row.released = "Y";
    });
  }

  function handleWithdraw(row: FormProps) {
    updateNotice({ id: row.id, released: "N" }).then(() => {
      row.released = "N";
    });
  }

  /** 批量删除 */
  function handleBatchDelete() {
    // 返回当前选中的行
    const curSelected = tableRef.value.getTableRef().getSelectionRows();
    const ids = getKeyList(curSelected, "id").join();
    deleteNotice(ids).then(() => {
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
    const page = await getNoticePage(toRaw(form));
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

  function openEditDialog(title = "新增", row?: NoticeVO) {
    addDialog({
      title: `${title}`,
      props: {
        data: {
          id: row?.id ?? null,
          name: row?.name ?? "",
          description: row?.description ?? ""
        }
      },
      width: "80%",
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
              updateNotice(curData).then(() => {
                chores();
              });
            } else {
              createNotice(curData).then(() => {
                chores();
              });
            }
          }
        });
      }
    });
  }

  function openDetailDialog(title = "通知/公告", row?: NoticeVO) {
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
    handleRelease,
    handleWithdraw,
    handleSelectionCancel,
    handleSizeChange,
    handleCurrentChange,
    handleSelectionChange
  };
}

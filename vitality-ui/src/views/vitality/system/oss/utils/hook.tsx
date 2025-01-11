import dayjs from "dayjs";
import editForm from "../form.vue";
import { addDialog } from "@/components/ReDialog";
import type { FormItemProps, OssVO } from "../utils/types";
import type { PaginationProps } from "@pureadmin/table";
import { deviceDetection, downloadByData, getKeyList } from "@pureadmin/utils";
import {
  getOssPage,
  createOss,
  updateOss,
  deleteOss,
  download
} from "@/api/system/oss";
import { reactive, ref, onMounted, h, toRaw, type Ref } from "vue";

export function useOss(tableRef: Ref) {
  const form = reactive<OssVO>({});
  const curRow = ref();
  const formRef = ref();
  const dataList = ref([]);
  const loading = ref(true);
  const selectedNum = ref(0);
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
      hide: false
    },
    {
      label: "名称",
      prop: "name",
      minWidth: 180
    },
    {
      label: "后缀名",
      prop: "suffix",
      minWidth: 70,
      hide: false
    },
    {
      label: "MD5",
      prop: "md5",
      minWidth: 200,
      hide: false
    },
    {
      label: "存储位置",
      prop: "storagePath",
      minWidth: 260,
      hide: true
    },
    {
      label: "创建者",
      minWidth: 100,
      prop: "createByName",
      hide: false
    },
    {
      label: "创建时间",
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
    deleteOss(row.id).then(r => {
      if (r.code === 200) {
        onSearch();
      }
    });
  }

  /** 批量删除 */
  function onBatchDel() {
    // 返回当前选中的行
    const curSelected = tableRef.value.getTableRef().getSelectionRows();
    const ids = getKeyList(curSelected, "id").join();
    deleteOss(ids).then(r => {
      if (r.code === 200) {
        tableRef.value.getTableRef().clearSelection();
        onSearch();
      }
    });
  }

  /** 当CheckBox选择项发生变化时会触发该事件 */
  function handleSelectionChange(val) {
    selectedNum.value = val.length;
    // 重置表格高度
    tableRef.value.setAdaptive();
  }

  /** 取消选择 */
  function onSelectionCancel() {
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

  function handleDownload(row) {
    download(row.id).then((data: BlobPart) => {
      downloadByData(data, row.name);
    });
  }

  async function onSearch() {
    loading.value = true;

    form.current = pagination.currentPage;
    form.size = pagination.pageSize;
    const page = await getOssPage(toRaw(form));

    dataList.value = page.records;
    pagination.pageSize = page.size;
    pagination.currentPage = page.current;
    pagination.total = page.total;

    setTimeout(() => {
      loading.value = false;
    }, 500);
  }

  const resetForm = formEl => {
    if (!formEl) return;
    formEl.resetFields();
    onSearch();
  };

  function openDialog(title = "新增", row?: FormItemProps) {
    addDialog({
      title: `${title}`,
      props: {
        formInline: {
          id: row?.id ?? null,
          name: row?.name ?? "",
          suffix: row?.suffix ?? "",
          storagePath: row?.storagePath ?? ""
        }
      },
      width: "40%",
      draggable: true,
      fullscreen: deviceDetection(),
      fullscreenIcon: true,
      closeOnClickModal: false,
      contentRenderer: () => h(editForm, { ref: formRef, formInline: null }),
      beforeSure: (done, { options }) => {
        const FormRef = formRef.value.getRef();
        const curData = options.props.formInline as FormItemProps;
        function chores() {
          done(); // 关闭弹框
          onSearch(); // 刷新表格数据
        }
        FormRef.validate(valid => {
          if (valid) {
            console.log("curData", curData);
            // 表单规则校验通过
            if (curData.id) {
              updateOss(curData).then(r => {
                if (r.code === 200) {
                  chores();
                }
              });
            } else {
              createOss(curData).then(r => {
                if (r.code === 200) {
                  chores();
                }
              });
            }
          }
        });
      }
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
    openDialog,
    handleDelete,
    onBatchDel,
    onSelectionCancel,
    handleSizeChange,
    handleCurrentChange,
    handleSelectionChange,
    handleDownload
  };
}

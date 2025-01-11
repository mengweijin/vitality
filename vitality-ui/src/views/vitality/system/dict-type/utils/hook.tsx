import dayjs from "dayjs";
import editForm from "../form.vue";
import { addDialog } from "@/components/ReDialog";
import type { FormItemProps, DictTypeVO } from "../utils/types";
import type { PaginationProps } from "@pureadmin/table";
import { deviceDetection, getKeyList } from "@pureadmin/utils";
import {
  getDictTypePage,
  createDictType,
  updateDictType,
  deleteDictType
} from "@/api/system/dict-type";
import { reactive, ref, onMounted, h, toRaw, type Ref } from "vue";
import dictDataPage from "@/views/vitality/system/dict-data/index.vue";

export function useDictType(tableRef: Ref) {
  const form = reactive<DictTypeVO>({});
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
      hide: true
    },
    {
      label: "名称",
      prop: "name",
      minWidth: 180,
      cellRenderer: ({ row, props }) => (
        <el-button
          key={row.name}
          size={props.size}
          type="primary"
          link
          style="text-decoration: underline;"
          onClick={() => onNameClick(row)}
        >
          {row.name}
        </el-button>
      )
    },
    {
      label: "编码",
      prop: "code",
      minWidth: 180
    },
    {
      label: "备注",
      prop: "remark",
      minWidth: 160,
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
      hide: false
    },
    {
      label: "更新时间",
      minWidth: 160,
      prop: "updateTime",
      formatter: ({ updateTime }) =>
        dayjs(updateTime).format("YYYY-MM-DD HH:mm:ss"),
      hide: false
    },
    {
      label: "操作",
      fixed: "right",
      width: 160,
      slot: "operation"
    }
  ];

  function onNameClick(row?: DictTypeVO) {
    addDialog({
      title: `${"字典：" + row?.name}`,
      props: {
        dictCode: row?.code ?? ""
      },
      width: "70%",
      draggable: true,
      fullscreen: false,
      fullscreenIcon: true,
      closeOnClickModal: false,
      hideFooter: true,
      contentRenderer: () => h(dictDataPage, { dictCode: null })
    });
  }

  function handleDelete(row) {
    deleteDictType(row.id).then(r => {
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
    deleteDictType(ids).then(r => {
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

  async function onSearch() {
    loading.value = true;

    form.current = pagination.currentPage;
    form.size = pagination.pageSize;
    const page = await getDictTypePage(toRaw(form));

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
          code: row?.code ?? "",
          remark: row?.remark ?? ""
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
              updateDictType(curData).then(r => {
                if (r.code === 200) {
                  chores();
                }
              });
            } else {
              createDictType(curData).then(r => {
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
    handleSelectionChange
  };
}

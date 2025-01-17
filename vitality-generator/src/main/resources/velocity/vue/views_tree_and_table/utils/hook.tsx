import { reactive, ref, onMounted, h, toRaw, type Ref } from "vue";
import dayjs from "dayjs";
import { deviceDetection, getKeyList } from "@pureadmin/utils";
import { ElMessageBox } from "element-plus";
import { addDialog } from "@/components/ReDialog";
import { handleTree } from "@/utils/tree";
import EditPage from "../edit.vue";
import DetailPage from "../detail.vue";
import type { FormProps, UserVO } from "../utils/types";
import type { PaginationProps } from "@pureadmin/table";
import ReDictTag from "@/components/ReDictTag";

import {
  getUserPage,
  createUser,
  updateUser,
  deleteUser
} from "@/api/system/user";
import { getDeptList } from "@/api/system/dept";

export function useUser(tableRef: Ref, treeRef: Ref) {
  const form = reactive<UserVO>({});
  const formRef = ref();
  const dataList = ref([]);
  const higherOptions = ref();
  const treeData = ref([]);
  const treeLoading = ref(true);
  const selectedNum = ref(0);
  const curRow = ref();
  const loading = ref(true);
  const switchLoadMap = ref({});

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
      prop: "username",
      minWidth: 180
    },
    {
      label: "状态",
      prop: "disabled",
      minWidth: 90,
      cellRenderer: scope => (
        <ReDictTag
          code="vtl_disabled"
          size="small"
          value={scope.row.disabled}
        ></ReDictTag>
      )
    },
    {
      label: "状态",
      prop: "disabled",
      minWidth: 90,
      cellRenderer: scope => (
        <el-switch
          size={scope.props.size === "small" ? "small" : "default"}
          loading={switchLoadMap.value[scope.index]?.loading}
          v-model={scope.row.disabled}
          active-value={"N"}
          inactive-value={"Y"}
          active-text="已启用"
          inactive-text="已停用"
          inline-prompt
          style={
            "--el-switch-on-color: #6abe39; --el-switch-off-color: #e84749"
          }
          before-change={() => beforeDisabledChange(scope as any)}
        />
      )
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
      width: 200,
      slot: "operation"
    }
  ];

  function beforeDisabledChange({ row, index }) {
    ElMessageBox.confirm(
      `确认要<strong>【${
        row.disabled === "Y" ? "启用" : "停用"
      }】</strong><strong style='color:var(--el-color-primary)'>${
        row.name
      } </strong>吗?`,
      "系统提示",
      {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
        dangerouslyUseHTMLString: true,
        draggable: true
      }
    )
      .then(() => {
        switchLoadMap.value[index] = Object.assign(
          {},
          switchLoadMap.value[index],
          {
            loading: true
          }
        );
      })
      .then(() => {
        let disabled: string = row.disabled === "Y" ? "N" : "Y";
        updateUser({ id: row.id, disabled: disabled } as FormProps).then(() => {
          row.disabled = disabled;
        });
      })
      .finally(() => {
        switchLoadMap.value[index] = Object.assign(
          {},
          switchLoadMap.value[index],
          {
            loading: false
          }
        );
      });
    return false;
  }

  function handleDelete(row) {
    deleteUser(row.id).then(() => {
      onSearch();
    });
  }

  /** 批量删除 */
  function handleBatchDelete() {
    // 返回当前选中的行
    const curSelected = tableRef.value.getTableRef().getSelectionRows();
    const ids = getKeyList(curSelected, "id").join();
    deleteUser(ids).then(() => {
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
    const page = await getUserPage(toRaw(form));
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
    form.deptId = "";
    treeRef.value.onTreeReset();
    onSearch();
  };

  function onTreeSelect({ id, selected }) {
    form.deptId = selected ? id : "";
    onSearch();
  }

  function formatHigherOptions(treeList) {
    // 根据返回数据的status字段值判断追加是否禁用disabled字段，返回处理后的树结构，用于上级部门级联选择器的展示（实际开发中也是如此，不可能前端需要的每个字段后端都会返回，这时需要前端自行根据后端返回的某些字段做逻辑处理）
    if (!treeList || !treeList.length) return;
    const newTreeList = [];
    for (let i = 0; i < treeList.length; i++) {
      treeList[i].disabled = treeList[i].disabled === "Y" ? true : false;
      formatHigherOptions(treeList[i].children);
      newTreeList.push(treeList[i]);
    }
    return newTreeList;
  }

  function openEditDialog(title = "新增", row?: UserVO) {
    addDialog({
      title: `${title}`,
      props: {
        data: {
          id: row?.id ?? null,
          name: row?.name ?? "",
          seq: row?.seq ?? 0,
          remark: row?.remark ?? "",
          deptId: row?.deptId ?? null
        },
        higherOptions: formatHigherOptions(higherOptions.value)
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
              updateUser(curData).then(() => {
                chores();
              });
            } else {
              createUser(curData).then(() => {
                chores();
              });
            }
          }
        });
      }
    });
  }

  function openDetailDialog(title = "详情", row?: UserVO) {
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

    treeLoading.value = true;
    // 归属部门
    const data = await getDeptList();
    higherOptions.value = handleTree(data);
    treeData.value = handleTree(data);
    treeLoading.value = false;
  });

  return {
    form,
    curRow,
    loading,
    columns,
    dataList,
    treeData,
    treeLoading,
    selectedNum,
    pagination,
    onSearch,
    onTreeSelect,
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

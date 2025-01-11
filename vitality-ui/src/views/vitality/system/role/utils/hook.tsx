import dayjs from "dayjs";
import editForm from "../form.vue";
import { handleTree } from "@/utils/tree";
// import { message } from "@/utils/message";
import { ElMessageBox } from "element-plus";
import { transformI18n } from "@/plugins/i18n";
import { addDialog } from "@/components/ReDialog";
import type { FormItemProps, RoleVO } from "../utils/types";
import type { PaginationProps } from "@pureadmin/table";
import { getKeyList, deviceDetection } from "@pureadmin/utils";
import {
  getRolePage,
  createRole,
  updateRole,
  deleteRole,
  setRolePermission
} from "@/api/system/role";
import { getMenuIdsByRoleId, getMenuList } from "@/api/system/menu";
import { type Ref, reactive, ref, onMounted, h, toRaw, watch } from "vue";

export function useRole(treeRef: Ref) {
  const form = reactive<RoleVO>({});
  const curRow = ref();
  const formRef = ref();
  const dataList = ref([]);
  const treeIds = ref([]);
  const treeData = ref([]);
  const isShow = ref(false);
  const loading = ref(true);
  const isLinkage = ref(false);
  const treeSearchValue = ref();
  const switchLoadMap = ref({});
  const isExpandAll = ref(false);
  const isSelectAll = ref(false);
  const treeProps = {
    value: "id",
    label: "title",
    children: "children"
  };
  const pagination = reactive<PaginationProps>({
    total: 0,
    pageSize: 10,
    currentPage: 1,
    background: true,
    pageSizes: [10, 20, 30, 50]
  });
  const columns: TableColumnList = [
    {
      label: "角色编号",
      prop: "id",
      minWidth: 180,
      align: "left",
      hide: true
    },
    {
      label: "角色名称",
      prop: "name",
      minWidth: 180
    },
    {
      label: "角色标识",
      prop: "code",
      minWidth: 180
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
    // {
    //   label: "状态",
    //   prop: "disabled",
    //   minWidth: 70,
    //   cellRenderer: ({ row, props }) => (
    //     <el-tag
    //       size={props.size}
    //       type={row.disabled === "Y" ? "danger" : "success"}
    //       effect="dark"
    //     >
    //       {row.disabled === "Y" ? "停用" : "启用"}
    //     </el-tag>
    //   )
    // },
    {
      label: "排序",
      prop: "seq",
      minWidth: 70,
      hide: false
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
      width: 210,
      slot: "operation"
    }
  ];
  // const buttonClass = computed(() => {
  //   return [
  //     "!h-[20px]",
  //     "reset-margin",
  //     "!text-gray-500",
  //     "dark:!text-white",
  //     "dark:hover:!text-primary"
  //   ];
  // });

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
        updateRole({ id: row.id, disabled: disabled } as FormItemProps).then(
          () => {
            row.disabled = disabled;
          }
        );
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
    deleteRole(row.id).then(r => {
      if (r.code === 200) {
        onSearch();
      }
    });
  }

  function handleSizeChange(val: number) {
    pagination.pageSize = val;
    onSearch();
  }

  function handleCurrentChange(val: number) {
    pagination.currentPage = val;
    onSearch();
  }

  function handleSelectionChange(val) {
    console.log("handleSelectionChange", val);
  }

  async function onSearch() {
    loading.value = true;

    form.current = pagination.currentPage;
    form.size = pagination.pageSize;
    const page = await getRolePage(toRaw(form));

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
      title: `${title}角色`,
      props: {
        formInline: {
          id: row?.id ?? null,
          name: row?.name ?? "",
          code: row?.code ?? "",
          seq: row?.seq ?? 0,
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
              updateRole(curData).then(r => {
                if (r.code === 200) {
                  chores();
                }
              });
            } else {
              createRole(curData).then(r => {
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

  /** 菜单权限 */
  async function handleMenu(row?: any) {
    const { id } = row;
    if (id) {
      curRow.value = row;
      isShow.value = true;
      const data = await getMenuIdsByRoleId(id);
      treeRef.value.setCheckedKeys(data);
    } else {
      curRow.value = null;
      isShow.value = false;
    }
  }

  /** 高亮当前权限选中行 */
  function rowStyle({ row: { id } }) {
    return {
      cursor: "pointer",
      background: id === curRow.value?.id ? "var(--el-fill-color-light)" : ""
    };
  }

  /** 菜单权限-保存 */
  function handleSave() {
    const { id } = curRow.value;
    setRolePermission({
      roleId: id,
      menuIds: treeRef.value.getCheckedKeys()
    });

    // 根据用户 id 调用实际项目中菜单权限修改接口
    // console.log(id, treeRef.value.getCheckedKeys());
    // message(`角色名称为${name}的菜单权限修改成功`, {
    //   type: "success"
    // });
  }

  /** 数据权限 可自行开发 */
  // function handleDatabase() {}

  const onQueryChanged = (query: string) => {
    treeRef.value!.filter(query);
  };

  const filterMethod = (query: string, node) => {
    return transformI18n(node.title)!.includes(query);
  };

  onMounted(async () => {
    onSearch();
    let menuList = await getMenuList();
    treeData.value = handleTree(menuList);
    treeIds.value = getKeyList(menuList, "id");
  });

  watch(isExpandAll, val => {
    val
      ? treeRef.value.setExpandedKeys(treeIds.value)
      : treeRef.value.setExpandedKeys([]);
  });

  watch(isSelectAll, val => {
    val
      ? treeRef.value.setCheckedKeys(treeIds.value)
      : treeRef.value.setCheckedKeys([]);
  });

  return {
    form,
    isShow,
    curRow,
    loading,
    columns,
    rowStyle,
    dataList,
    treeData,
    treeProps,
    isLinkage,
    pagination,
    isExpandAll,
    isSelectAll,
    treeSearchValue,
    // buttonClass,
    onSearch,
    resetForm,
    openDialog,
    handleMenu,
    handleSave,
    handleDelete,
    filterMethod,
    transformI18n,
    onQueryChanged,
    // handleDatabase,
    handleSizeChange,
    handleCurrentChange,
    handleSelectionChange
  };
}

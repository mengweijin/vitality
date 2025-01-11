import dayjs from "dayjs";
import editForm from "../form.vue";
import { handleTree } from "@/utils/tree";
import {
  getDeptList,
  createDept,
  updateDept,
  deleteDept
} from "@/api/system/dept";
import { addDialog } from "@/components/ReDialog";
import { reactive, ref, onMounted, h } from "vue";
import type { FormItemProps } from "../utils/types";
import { cloneDeep, isAllEmpty, deviceDetection } from "@pureadmin/utils";
import ReDictTag from "@/components/ReDictTag";

export function useDept() {
  const form = reactive<FormItemProps>({});

  const formRef = ref();
  const dataList = ref([]);
  const loading = ref(true);

  const columns: TableColumnList = [
    {
      label: "部门名称",
      prop: "name",
      minWidth: 260,
      align: "left"
    },
    {
      label: "状态",
      prop: "disabled",
      minWidth: 70,
      cellRenderer: ({ row, props }) => (
        <ReDictTag
          size={props.size}
          code="vtl_disabled"
          value={row.disabled}
        ></ReDictTag>
      )
    },
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
      label: "ID",
      prop: "id",
      minWidth: 180,
      align: "left",
      hide: true
    },
    {
      label: "创建者",
      minWidth: 100,
      prop: "createByName"
    },
    {
      label: "创建时间",
      minWidth: 160,
      prop: "createTime",
      formatter: ({ createTime }) =>
        dayjs(createTime).format("YYYY-MM-DD HH:mm:ss")
    },
    {
      label: "更新者",
      minWidth: 100,
      prop: "updateByName"
    },
    {
      label: "更新时间",
      minWidth: 160,
      prop: "updateTime",
      formatter: ({ updateTime }) =>
        dayjs(updateTime).format("YYYY-MM-DD HH:mm:ss")
    },
    {
      label: "操作",
      fixed: "right",
      width: 210,
      slot: "operation"
    }
  ];

  function handleSelectionChange(val) {
    console.log("handleSelectionChange", val);
  }

  function resetForm(formEl) {
    if (!formEl) return;
    formEl.resetFields();
    onSearch();
  }

  async function onSearch() {
    loading.value = true;
    let deptList = await getDeptList(); // 这里是返回一维数组结构，前端自行处理成树结构，返回格式要求：唯一id加父节点parentId，parentId取父节点id
    if (!isAllEmpty(form.name)) {
      // 前端搜索部门名称
      deptList = deptList.filter(item => item.name.includes(form.name));
    }
    if (!isAllEmpty(form.disabled)) {
      // 前端搜索状态
      deptList = deptList.filter(item => item.disabled === form.disabled);
    }
    dataList.value = handleTree(deptList); // 处理成树结构
    setTimeout(() => {
      loading.value = false;
    }, 500);
  }

  function formatHigherDeptOptions(treeList) {
    // 根据返回数据的status字段值判断追加是否禁用disabled字段，返回处理后的树结构，用于上级部门级联选择器的展示（实际开发中也是如此，不可能前端需要的每个字段后端都会返回，这时需要前端自行根据后端返回的某些字段做逻辑处理）
    if (!treeList || !treeList.length) return;
    const newTreeList = [];
    for (let i = 0; i < treeList.length; i++) {
      treeList[i].disabled = treeList[i].disabled === "Y" ? true : false;
      formatHigherDeptOptions(treeList[i].children);
      newTreeList.push(treeList[i]);
    }
    return newTreeList;
  }

  function openDialog(title = "新增", row?: FormItemProps) {
    addDialog({
      title: `${title}部门`,
      props: {
        formInline: {
          id: row?.id ?? null,
          parentId: row?.parentId ?? "0",
          name: row?.name ?? "",
          seq: row?.seq ?? 0,
          disabled: row?.disabled ?? "N",
          remark: row?.remark ?? ""
        },
        higherDeptOptions: formatHigherDeptOptions(cloneDeep(dataList.value))
      },
      width: "40%",
      draggable: true,
      fullscreen: deviceDetection(),
      fullscreenIcon: true,
      closeOnClickModal: false,
      contentRenderer: () =>
        h(editForm, {
          ref: formRef,
          formInline: null,
          higherDeptOptions: null
        }),
      beforeSure: (done, { options }) => {
        const FormRef = formRef.value.getRef();
        const curData = options.props.formInline as FormItemProps;
        function chores() {
          done(); // 关闭弹框
          onSearch(); // 刷新表格数据
        }
        FormRef.validate(valid => {
          if (valid) {
            // 表单规则校验通过
            if (curData.id) {
              updateDept(curData).then(r => {
                if (r.code === 200) {
                  chores();
                }
              });
            } else {
              createDept(curData).then(r => {
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

  function handleDelete(row: { id: String }) {
    deleteDept(row.id).then(r => {
      if (r.code === 200) {
        onSearch();
      }
    });
  }

  onMounted(() => {
    onSearch();
  });

  return {
    form,
    loading,
    columns,
    dataList,
    /** 搜索 */
    onSearch,
    /** 重置 */
    resetForm,
    /** 新增、修改部门 */
    openDialog,
    /** 删除部门 */
    handleDelete,
    handleSelectionChange
  };
}

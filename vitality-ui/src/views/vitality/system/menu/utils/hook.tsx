import editForm from "../form.vue";
import { handleTree } from "@/utils/tree";
import { message } from "@/utils/message";
import { getMenuList } from "@/api/system/menu";
import { transformI18n } from "@/plugins/i18n";
import { addDialog } from "@/components/ReDialog";
import { reactive, ref, onMounted, h } from "vue";
import type { FormItemProps } from "../utils/types";
import { useRenderIcon } from "@/components/ReIcon/src/hooks";
import { cloneDeep, isAllEmpty, deviceDetection } from "@pureadmin/utils";
import dayjs from "dayjs";

export function useMenu() {
  const form = reactive({
    title: ""
  });

  const formRef = ref();
  const dataList = ref([]);
  const loading = ref(true);

  const getMenuType = (type, text = false) => {
    switch (type) {
      case "MENU":
        return text ? "菜单" : "success";
      case "IFRAME":
        return text ? "内嵌页" : "warning";
      case "URL":
        return text ? "外链" : "danger";
      case "BTN":
        return text ? "按钮" : "info";
    }
  };

  const columns: TableColumnList = [
    {
      label: "菜单名称",
      prop: "title",
      align: "left",
      cellRenderer: ({ row }) => (
        <>
          <span class="inline-block mr-1">
            {h(useRenderIcon(row.icon), {
              style: { paddingTop: "1px" }
            })}
          </span>
          <span>{transformI18n(row.title)}</span>
        </>
      ),
      minWidth: 200
    },
    {
      label: "菜单类型",
      prop: "type",
      minWidth: 100,
      cellRenderer: ({ row, props }) => (
        <el-tag size={props.size} type={getMenuType(row.type)} effect="dark">
          {getMenuType(row.type, true)}
        </el-tag>
      )
    },
    {
      label: "路由路径",
      prop: "routerPath",
      align: "left",
      minWidth: 160
    },
    // {
    //   label: "组件路径",
    //   prop: "component",
    //   formatter: ({ path, component }) =>
    //     isAllEmpty(component) ? path : component
    // },
    {
      label: "权限标识",
      prop: "permission",
      align: "left",
      minWidth: 200
    },
    {
      label: "隐藏",
      prop: "showLink",
      formatter: ({ showLink }) => (showLink === "Y" ? "否" : "是"),
      minWidth: 60
    },
    {
      label: "状态",
      prop: "disabled",
      minWidth: 70,
      cellRenderer: ({ row, props }) => (
        <el-tag
          size={props.size}
          type={row.disabled === "Y" ? "danger" : "success"}
          effect="dark"
        >
          {row.disabled === "Y" ? "停用" : "启用"}
        </el-tag>
      )
    },
    {
      label: "排序",
      prop: "seq",
      minWidth: 60
    },
    {
      label: "创建者",
      minWidth: 100,
      prop: "createByName",
      hide: true
    },
    {
      label: "创建时间",
      minWidth: 160,
      prop: "createTime",
      formatter: ({ createTime }) =>
        dayjs(createTime).format("YYYY-MM-DD HH:mm:ss"),
      hide: true
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
    let newData = await getMenuList(); // 这里是返回一维数组结构，前端自行处理成树结构，返回格式要求：唯一id加父节点parentId，parentId取父节点id
    if (!isAllEmpty(form.title)) {
      // 前端搜索菜单名称
      newData = newData.filter(item =>
        transformI18n(item.title).includes(form.title)
      );
    }
    dataList.value = handleTree(newData); // 处理成树结构
    setTimeout(() => {
      loading.value = false;
    }, 500);
  }

  function formatHigherMenuOptions(treeList) {
    if (!treeList || !treeList.length) return;
    const newTreeList = [];
    for (let i = 0; i < treeList.length; i++) {
      treeList[i].title = transformI18n(treeList[i].title);
      formatHigherMenuOptions(treeList[i].children);
      newTreeList.push(treeList[i]);
    }
    return newTreeList;
  }

  function openDialog(title = "新增", row?: FormItemProps) {
    addDialog({
      title: `${title}菜单`,
      props: {
        formInline: {
          type: row?.type ?? "MENU",
          higherMenuOptions: formatHigherMenuOptions(cloneDeep(dataList.value)),
          parentId: row?.parentId ?? 0,
          title: row?.title ?? "",
          routerName: row?.routerName ?? "",
          routerPath: row?.routerPath ?? "",
          componentPath: row?.componentPath ?? "",
          seq: row?.seq ?? 99,
          redirect: row?.redirect ?? "",
          icon: row?.icon ?? "",
          extraIcon: row?.extraIcon ?? "",
          enterTransition: row?.enterTransition ?? "",
          leaveTransition: row?.leaveTransition ?? "",
          activePath: row?.activePath ?? "",
          permission: row?.permission ?? "",
          iframeSrc: row?.iframeSrc ?? "",
          iframeLoading: row?.iframeLoading ?? true,
          keepAlive: row?.keepAlive ?? false,
          hiddenTag: row?.hiddenTag ?? false,
          fixedTag: row?.fixedTag ?? false,
          showLink: row?.showLink ?? true,
          showParent: row?.showParent ?? false
        }
      },
      width: "55%",
      draggable: true,
      fullscreen: deviceDetection(),
      fullscreenIcon: true,
      closeOnClickModal: false,
      contentRenderer: () => h(editForm, { ref: formRef }),
      beforeSure: (done, { options }) => {
        const FormRef = formRef.value.getRef();
        const curData = options.props.formInline as FormItemProps;
        function chores() {
          message(
            `您${title}了菜单名称为${transformI18n(curData.title)}的这条数据`,
            {
              type: "success"
            }
          );
          done(); // 关闭弹框
          onSearch(); // 刷新表格数据
        }
        FormRef.validate(valid => {
          if (valid) {
            console.log("curData", curData);
            // 表单规则校验通过
            if (title === "新增") {
              // 实际开发先调用新增接口，再进行下面操作
              chores();
            } else {
              // 实际开发先调用修改接口，再进行下面操作
              chores();
            }
          }
        });
      }
    });
  }

  function handleDelete(row) {
    message(`您删除了菜单名称为${transformI18n(row.title)}的这条数据`, {
      type: "success"
    });
    onSearch();
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
    /** 新增、修改菜单 */
    openDialog,
    /** 删除菜单 */
    handleDelete,
    handleSelectionChange
  };
}

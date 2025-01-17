import type { TableInfoVO } from "../utils/types";
import { h, onMounted, reactive, ref, toRaw } from "vue";
import { getTableList } from "@/api/tool/generator";
import ReDictTag from "@/components/ReDictTag";
import { addDialog } from "@/components/ReDialog";
// import { deviceDetection } from "@pureadmin/utils";
import generatorPage from "../gen.vue";

export function useGenerator() {
  const form = reactive<TableInfoVO>({});
  const dataList = ref([]);
  const isShow = ref(false);
  const loading = ref(true);

  const columns: TableColumnList = [
    {
      label: "表名称",
      prop: "name",
      align: "left",
      fixed: "left",
      minWidth: 110
    },
    {
      label: "存在主键",
      prop: "havePrimaryKey",
      width: 80,
      cellRenderer: ({ row, props }) => (
        <ReDictTag
          size={props.size}
          code="vtl_yes_no"
          value={row.havePrimaryKey ? "Y" : "N"}
        ></ReDictTag>
      )
    },
    {
      label: "字段",
      prop: "fieldNames",
      align: "left",
      minWidth: 300
    },
    {
      label: "表注释信息",
      prop: "comment",
      minWidth: 50,
      hide: true
    },
    {
      label: "操作",
      fixed: "right",
      width: 110,
      slot: "operation"
    }
  ];

  async function onSearch() {
    loading.value = true;

    dataList.value = await getTableList(toRaw(form));

    setTimeout(() => {
      loading.value = false;
    }, 500);
  }

  function openDialog(title = "生成代码", row?: TableInfoVO) {
    addDialog({
      title: `${title + "【" + row.name + "】"}`,
      props: {
        formInline: {
          name: row?.name ?? "",
          havePrimaryKey: row?.havePrimaryKey ?? "",
          fieldNames: row?.fieldNames ?? "",
          remark: row?.comment ?? ""
        }
      },
      width: "90%",
      draggable: true,
      fullscreen: true,
      // fullscreen: deviceDetection(),
      fullscreenIcon: true,
      closeOnClickModal: false,
      hideFooter: true,
      contentRenderer: () => h(generatorPage, { formInline: null })
    });
  }

  const resetForm = formEl => {
    if (!formEl) return;
    formEl.resetFields();
    onSearch();
  };

  onMounted(async () => {
    onSearch();
  });

  return {
    form,
    isShow,
    loading,
    columns,
    dataList,
    onSearch,
    resetForm,
    openDialog
  };
}

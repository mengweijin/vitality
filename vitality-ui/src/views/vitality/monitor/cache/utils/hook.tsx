import { reactive, ref, onMounted, type Ref } from "vue";
import type { CacheVO } from "./types";
import { deleteUser } from "@/api/system/user";

export function useCache(treeRef: Ref) {
  const form = reactive<CacheVO>({});
  const treeData = ref([]);
  const treeLoading = ref(true);

  function handleDelete(row) {
    deleteUser(row.id).then(() => {
      onSearch();
    });
  }

  async function onSearch() {}

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

  onMounted(async () => {});

  return {
    form,
    treeData,
    treeLoading,
    onSearch,
    onTreeSelect,
    resetForm,
    handleDelete
  };
}

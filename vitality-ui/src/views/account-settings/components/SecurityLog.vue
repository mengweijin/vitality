<script setup lang="ts">
import { reactive, ref, onMounted, toRaw } from "vue";
import { deviceDetection } from "@pureadmin/utils";
import type { PaginationProps } from "@pureadmin/table";
import { getLogLoginPage } from "@/api/monitor/log-login";
import { LogLoginVO } from "@/views/vitality/monitor/log-login/utils/types";
import { useSecurityLogColumns } from "./utils/hook";

defineOptions({
  name: "SecurityLog"
});

const loading = ref(true);
const form = reactive<LogLoginVO>({});
const tableRef = ref();
const dataList = ref([]);

const pagination = reactive<PaginationProps>({
  total: 0,
  pageSize: 10,
  currentPage: 1,
  background: true,
  pageSizes: [10, 20, 30, 50]
});

const { securityLogColumns } = useSecurityLogColumns();

async function onSearch() {
  loading.value = true;

  form.current = pagination.currentPage;
  form.size = pagination.pageSize;
  const page = await getLogLoginPage(toRaw(form));
  dataList.value = page.records;
  pagination.total = page.total;
  pagination.pageSize = page.size;
  pagination.currentPage = page.current;

  setTimeout(() => {
    loading.value = false;
  }, 200);
}

function handleSizeChange(val: number) {
  pagination.pageSize = val;
  onSearch();
}

function handleCurrentChange(val: number) {
  pagination.currentPage = val;
  onSearch();
}

onMounted(() => {
  onSearch();
});
</script>

<template>
  <div
    :class="[
      'min-w-[180px]',
      'ml-[50px]',
      deviceDetection() ? 'max-w-[100%]' : 'max-w-[90%]'
    ]"
  >
    <h3 class="my-8">安全日志</h3>
    <pure-table
      ref="tableRef"
      row-key="id"
      align-whole="center"
      showOverflowTooltip
      table-layout="auto"
      :loading="loading"
      :data="dataList"
      :columns="securityLogColumns"
      :pagination="pagination"
      :header-cell-style="{
        background: 'var(--el-fill-color-light)',
        color: 'var(--el-text-color-primary)'
      }"
      @page-size-change="handleSizeChange"
      @page-current-change="handleCurrentChange"
    />
  </div>
</template>

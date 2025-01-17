<script setup lang="ts">
import { useMonitorApplication } from "./utils/hook";
import { computed, onMounted } from "vue";
import { PureTableBar } from "@/components/RePureTableBar";
import { useDescriptionsColumns } from "./utils/descriptions-columns";

defineOptions({
  name: "MonitorApplication"
});

const { loading, columns, monitorVO } = useMonitorApplication();
const { serverColumns, cpuColumns, memoryColumns, jvmColumns } =
  useDescriptionsColumns();

const server = computed(() => {
  return monitorVO?.value?.server ?? {};
});

const cpu = computed(() => {
  return monitorVO?.value?.cpu ?? {};
});

const memory = computed(() => {
  return monitorVO?.value?.memory ?? {};
});

const jvm = computed(() => {
  return monitorVO?.value?.jvm ?? {};
});

const disk = computed(() => {
  return monitorVO?.value?.disk ?? [];
});

onMounted(() => {});
</script>

<template>
  <div v-loading="loading" class="main">
    <PureDescriptions
      title="服务器信息"
      border
      :data="[server]"
      :columns="serverColumns"
      :column="2"
    />
    <PureDescriptions
      class="mt-5"
      title="处理器信息"
      border
      :data="[cpu]"
      :columns="cpuColumns"
      :column="2"
    />
    <PureDescriptions
      class="mt-5"
      title="内存信息"
      border
      :data="[memory]"
      :columns="memoryColumns"
      :column="1"
    />
    <PureDescriptions
      class="mt-5"
      title="JVM 信息"
      border
      :data="[jvm]"
      :columns="jvmColumns"
      :column="2"
    />
    <PureTableBar title="磁盘信息" :columns="columns" class="mt-5 mb-10">
      <template v-slot="{ size, dynamicColumns }">
        <pure-table
          row-key="mountName"
          align-whole="center"
          showOverflowTooltip
          table-layout="auto"
          :loading="loading"
          :size="size"
          :data="disk"
          :columns="dynamicColumns"
          :header-cell-style="{
            background: 'var(--el-fill-color-light)',
            color: 'var(--el-text-color-primary)'
          }"
        />
      </template>
    </PureTableBar>
  </div>
</template>

<style scoped lang="scss">
:deep(.el-dropdown-menu__item i) {
  margin: 0;
}

.main-content {
  margin: 24px 24px 0 !important;
}
</style>

<script setup lang="ts">
import { ref, computed } from "vue";
import type { NoticeVO, Props } from "./utils/types";
import ReDictTag from "@/components/ReDictTag";

const props = withDefaults(defineProps<Props>(), {
  data: () => ({})
});

const row = ref<NoticeVO>(props.data);

const showRemark = computed(() => {
  return true;
});
</script>

<template>
  <div class="flex justify-center pb-5">
    <h1>{{ row.name }}</h1>
  </div>

  <div v-html="row.description" />

  <div class="flex justify-end space-x-5 mt-5">
    <span>创建者：{{ row.createByName }}</span>
    <span>创建时间：{{ row.createTime }}</span>
    <span>更新者：{{ row.updateByName }}</span>
    <span>更新时间：{{ row.updateTime }}</span>
    <div class="inline-flex">
      <span>发布状态：</span>
      <ReDictTag code="vtl_released" size="small" :value="row.released" />
    </div>
  </div>
</template>

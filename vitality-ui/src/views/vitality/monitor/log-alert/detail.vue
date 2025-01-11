<script setup lang="ts">
import { ref, computed, withDefaults, defineProps, defineExpose } from "vue";
import { useDetailColumns } from "./utils/detail-columns";
import "vue-json-pretty/lib/styles.css";
import VueJsonPretty from "vue-json-pretty";
import type { LogAlertVO, Props } from "./utils/types";

const props = withDefaults(defineProps<Props>(), {
  data: () => ({})
});

const row = ref<LogAlertVO>(props.data);

const { detailColumns } = useDetailColumns();

const dataList = ref([
  {
    title: "堆栈信息",
    name: "stackTrace",
    json: false,
    data: row.value.stackTrace
  }
]);

const showRemark = computed(() => {
  return true;
});
</script>

<template>
  <div>
    <el-scrollbar>
      <PureDescriptions
        border
        :data="[row]"
        :columns="detailColumns"
        :column="2"
      />
    </el-scrollbar>
    <el-tabs :modelValue="'stackTrace'" type="border-card" class="mt-4">
      <template v-for="(item, index) in dataList" :key="index">
        <el-tab-pane :name="item.name" :label="item.title">
          <el-scrollbar max-height="calc(100vh - 280px)">
            <template v-if="item.data != null">
              <vue-json-pretty v-if="item.json" :data="JSON.parse(item.data)" />
              <div v-else>{{ item.data }}</div>
            </template>
          </el-scrollbar>
        </el-tab-pane>
      </template>
    </el-tabs>
  </div>
</template>

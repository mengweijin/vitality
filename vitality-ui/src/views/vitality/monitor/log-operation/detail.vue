<script setup lang="ts">
import { ref, computed } from "vue";
import { useDetailColumns } from "./utils/detail-columns";
import "vue-json-pretty/lib/styles.css";
import VueJsonPretty from "vue-json-pretty";
import type { FormProps } from "./utils/types";

const props = withDefaults(defineProps<FormProps>(), {
  row: () => ({})
});

const { detailColumns } = useDetailColumns();

const dataList = ref([
  {
    title: "请求数据",
    name: "requestData",
    json: true,
    data: (props.row as any).requestData
  },
  {
    title: "响应数据",
    name: "responseData",
    json: true,
    data: (props.row as any).responseData
  },
  {
    title: "失败信息",
    name: "errorMsg",
    data: (props.row as any).errorMsg
  }
]);

const showErrorMsg = computed(() => {
  return (props.row as any).success === "Y";
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
    <el-tabs :modelValue="'requestData'" type="border-card" class="mt-4">
      <template v-for="(item, index) in dataList" :key="index">
        <template v-if="item.name === 'errorMsg' && showErrorMsg" />
        <el-tab-pane v-else :name="item.name" :label="item.title">
          <el-scrollbar max-height="calc(100vh - 360px)">
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

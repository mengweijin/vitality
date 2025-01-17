<script setup lang="ts">
import { ref, computed } from "vue";
import { useDetailColumns } from "./utils/detail-columns";
import "vue-json-pretty/lib/styles.css";
import VueJsonPretty from "vue-json-pretty";
import type { PostVO, Props } from "./utils/types";

const props = withDefaults(defineProps<Props>(), {
  data: () => ({})
});

const row = ref<PostVO>(props.data);

const { detailColumns } = useDetailColumns();

const dataList = ref([
  {
    title: "备注",
    name: "remark",
    json: true,
    data: row.value.remark
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
    <el-tabs :modelValue="'remark'" type="border-card" class="mt-4">
      <template v-for="(item, index) in dataList" :key="index">
        <template v-if="item.name === 'remark' && showRemark" />
        <el-tab-pane v-else :name="item.name" :label="item.title">
          <el-scrollbar max-height="calc(100vh - 240px)">
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

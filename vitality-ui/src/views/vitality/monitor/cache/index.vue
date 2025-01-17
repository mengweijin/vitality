<script setup lang="ts">
import { useCache } from "./utils/hook";
import { ref, onMounted, watch } from "vue";
import tree from "./tree.vue";
import "vue-json-pretty/lib/styles.css";
import VueJsonPretty from "vue-json-pretty";
import { deviceDetection } from "@pureadmin/utils";
import { useRenderIcon } from "@/components/ReIcon/src/hooks";
import Delete from "@iconify-icons/ep/delete";
import Refresh from "@iconify-icons/ep/refresh";

defineOptions({
  name: "MonitorCache"
});

const treeRef = ref();
const formRef = ref();
const cacheValue = ref();

const {
  form,
  treeData,
  treeLoading,
  onSearch,
  onTreeSelect,
  resetForm,
  handleDelete
} = useCache(treeRef);

function handleCacheKey({ name, key, value }) {
  cacheValue.value = value;
}

function resetCacheValue() {
  cacheValue.value = null;
}

onMounted(() => {});
</script>

<template>
  <div :class="['flex', 'justify-between', deviceDetection() && 'flex-wrap']">
    <tree
      ref="treeRef"
      :class="['mr-2', deviceDetection() ? 'w-full' : 'min-w-[300px]']"
      :treeData="treeData"
      :treeLoading="treeLoading"
      @tree-select="handleCacheKey"
      @reset-cache-value="resetCacheValue()"
    />
    <div
      :class="[deviceDetection() ? ['w-full', 'mt-2'] : 'w-[calc(100%-300px)]']"
    >
      <el-form
        v-if="false"
        ref="formRef"
        :inline="true"
        :model="form"
        class="search-form bg-bg_color w-[99/100] pl-8 pt-[12px] overflow-auto"
      >
        <el-form-item>
          <el-button
            type="primary"
            :icon="useRenderIcon(Refresh)"
            @click="onSearch"
          >
            刷新
          </el-button>
          <el-button
            type="danger"
            :icon="useRenderIcon(Delete)"
            @click="resetForm(formRef)"
          >
            删除
          </el-button>
        </el-form-item>
      </el-form>

      <div
        class="bg-bg_color pl-8 pt-[12px] overflow-auto"
        :style="{
          minHeight: `calc(100vh - 141px)`,
          maxHeight: `calc(100vh - 141px)`
        }"
      >
        <template v-if="cacheValue != null">
          <vue-json-pretty :data="cacheValue" />
        </template>
      </div>
    </div>
  </div>
</template>

<style scoped lang="scss">
.main-content {
  margin: 24px 24px 0 !important;
}

.search-form {
  :deep(.el-form-item) {
    margin-bottom: 12px;
  }
}
</style>

<script setup lang="ts">
import { useRenderIcon } from "@/components/ReIcon/src/hooks";
import {
  ref,
  computed,
  watch,
  getCurrentInstance,
  reactive,
  onMounted
} from "vue";

import Reset from "@iconify-icons/ri/restart-line";
import More2Fill from "@iconify-icons/ri/more-2-fill";
import Minus from "@iconify-icons/ep/minus";
import Delete from "@iconify-icons/ep/delete";
import ExpandIcon from "@/assets/svg/expand.svg?component";
import UnExpandIcon from "@/assets/svg/unexpand.svg?component";
import { CacheVO } from "./utils/types";
import {
  deleteCacheByName,
  getCacheByName,
  getCacheNameList
} from "@/api/monitor/cache";

interface Tree {
  id: number;
  name: string;
  highlight?: boolean;
  children?: Tree[];
}

const emit = defineEmits(["tree-select", "reset-cache-value"]);

const form = reactive<CacheVO>({});
const cacheNameList = ref([]);
const treeData = ref([]);
const treeLoading = ref(false);

const treeRef = ref();
const isExpand = ref(true);
const searchValue = ref("");
const highlightMap = ref({});
const { proxy } = getCurrentInstance();
const defaultProps = {
  children: "children",
  label: "name"
};
const buttonClass = computed(() => {
  return [
    "!h-[20px]",
    "!text-sm",
    "reset-margin",
    "!text-[var(--el-text-color-regular)]",
    "dark:!text-white",
    "dark:hover:!text-primary"
  ];
});

const filterNode = (value: string, data: Tree) => {
  if (!value) return true;
  return data.name.includes(value);
};

function nodeClick(value) {
  const nodeId = value.$treeNodeId;
  highlightMap.value[nodeId] = highlightMap.value[nodeId]?.highlight
    ? Object.assign({ id: nodeId }, highlightMap.value[nodeId], {
        highlight: false
      })
    : Object.assign({ id: nodeId }, highlightMap.value[nodeId], {
        highlight: true
      });
  Object.values(highlightMap.value).forEach((v: Tree) => {
    if (v.id !== nodeId) {
      v.highlight = false;
    }
  });
  emit(
    "tree-select",
    highlightMap.value[nodeId]?.highlight
      ? Object.assign({ ...value, selected: true })
      : Object.assign({ ...value, selected: false })
  );
}

function toggleRowExpansionAll(status) {
  isExpand.value = status;
  const nodes = (proxy.$refs["treeRef"] as any).store._getAllNodes();
  for (let i = 0; i < nodes.length; i++) {
    nodes[i].expanded = status;
  }
}

/** 重置部门树状态（选中状态、搜索框值、树初始化） */
function onTreeReset() {
  highlightMap.value = {};
  searchValue.value = "";
  toggleRowExpansionAll(true);
}

function removeCache(node, data) {
  console.log(node);
  console.log(data);
  deleteCacheByName(form.cacheName, data.key).then(() => {
    const parent = node.parent;
    const children = parent.data.children || parent.data;
    const index = children.findIndex(d => d.key === data.key);
    children.splice(index, 1);
    emit("reset-cache-value");
  });
}

watch(searchValue, val => {
  treeRef.value!.filter(val);
});

watch(form, async ({ cacheName }) => {
  treeLoading.value = true;
  const data = await getCacheByName(cacheName);
  //treeData.value = handleTree(data);
  treeData.value = data;
  treeLoading.value = false;

  emit("reset-cache-value");
});

defineExpose({ onTreeReset });

onMounted(async () => {
  cacheNameList.value = await getCacheNameList();
});
</script>

<template>
  <div
    v-loading="treeLoading"
    class="h-full bg-bg_color overflow-auto"
    :style="{
      minHeight: `calc(100vh - 141px)`,
      maxHeight: `calc(100vh - 141px)`
    }"
  >
    <el-select
      v-model="form.cacheName"
      placeholder="请选择缓存名称"
      filterable
      class="!w-[180px]"
      style="width: 100% !important; padding: 8px"
    >
      <el-option
        v-for="(item, index) in cacheNameList"
        :key="index"
        :label="item"
        :value="item"
      />
    </el-select>
    <div class="flex items-center h-[34px]">
      <el-input
        v-model="searchValue"
        class="ml-2"
        size="small"
        placeholder="请输入缓存 KEY 值"
        clearable
        style="padding-right: 8px"
      >
        <template #suffix>
          <el-icon class="el-input__icon">
            <IconifyIconOffline
              v-show="searchValue.length === 0"
              icon="ri:search-line"
            />
          </el-icon>
        </template>
      </el-input>
      <el-dropdown v-if="false" :hide-on-click="false">
        <IconifyIconOffline
          class="w-[28px] cursor-pointer"
          width="18px"
          :icon="More2Fill"
        />
        <template #dropdown>
          <el-dropdown-menu>
            <el-dropdown-item>
              <el-button
                :class="buttonClass"
                link
                type="primary"
                :icon="useRenderIcon(isExpand ? ExpandIcon : UnExpandIcon)"
                @click="toggleRowExpansionAll(isExpand ? false : true)"
              >
                {{ isExpand ? "折叠全部" : "展开全部" }}
              </el-button>
            </el-dropdown-item>
            <el-dropdown-item>
              <el-button
                :class="buttonClass"
                link
                type="primary"
                :icon="useRenderIcon(Reset)"
                @click="onTreeReset"
              >
                重置状态
              </el-button>
            </el-dropdown-item>
          </el-dropdown-menu>
        </template>
      </el-dropdown>
    </div>
    <el-divider />
    <el-tree
      ref="treeRef"
      :data="treeData"
      node-key="id"
      size="small"
      :props="defaultProps"
      default-expand-all
      :expand-on-click-node="false"
      :filter-node-method="filterNode"
    >
      <template #default="{ node, data }">
        <span
          style="
            display: flex;
            flex-direction: row;
            justify-content: space-between;
            width: 100%;
            cursor: default;
          "
        >
          <span
            :class="[
              'pl-1',
              'pr-1',
              'rounded',
              'flex',
              'items-center',
              'select-none',
              'hover:text-primary',
              searchValue.trim().length > 0 &&
                node.label.includes(searchValue) &&
                'text-red-500',
              highlightMap[node.id]?.highlight ? 'dark:text-primary' : ''
            ]"
            :style="{
              color: highlightMap[node.id]?.highlight
                ? 'var(--el-color-primary)'
                : '',
              background: highlightMap[node.id]?.highlight
                ? 'var(--el-color-primary-light-7)'
                : 'transparent'
            }"
            @click="nodeClick(data)"
          >
            <IconifyIconOffline :icon="Minus" />
            <a style="padding: 0 15px">
              {{ node.label }}
            </a>
          </span>
          <span
            :class="[
              'pl-1',
              'pr-3',
              'rounded',
              'flex',
              'items-center',
              'select-none',
              'hover:text-primary'
            ]"
          >
            <a style="margin-left: 8px" @click="removeCache(node, data)">
              <IconifyIconOffline :icon="Delete" />
            </a>
          </span>
        </span>
      </template>
    </el-tree>
  </div>
</template>

<style lang="scss" scoped>
:deep(.el-divider) {
  margin: 0;
}

:deep(.el-tree) {
  --el-tree-node-hover-bg-color: transparent;
}
</style>

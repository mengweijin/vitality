<script setup lang="ts">
import { computed, onMounted, ref } from "vue";
import { DictDataVO, getDictDataListByCode } from "@/api/system/dict";
import { isEmpty } from "@pureadmin/utils";
import type { EpPropMergeType } from "element-plus/es/utils/vue/props/types";
import { useDictStoreHook } from "@/store/modules/dict";

interface Props {
  /** 字典类型编码 */
  code: string;
  /** 当前字典值 */
  value?: string | Array<String>;
  separator?: string;
  size?: EpPropMergeType<
    StringConstructor,
    "" | "default" | "small" | "large",
    unknown
  >;
}

const props = withDefaults(defineProps<Props>(), {
  code: "",
  value: null,
  separator: ",",
  size: "default"
});

const options = ref<DictDataVO[]>([]);

const tagType = ["success", "primary", "info", "warning", "danger"];

const values = computed(() => {
  if (!props.value || isEmpty(props.value)) {
    return [];
  }
  if (Array.isArray(props.value)) {
    return props.value;
  } else {
    return String(props.value).split(props.separator);
  }
});

onMounted(() => {
  options.value = useDictStoreHook().getDicts(props.code);
});

function getTagType(index: any) {
  let defaultType = tagType[2];
  if (index < tagType.length) {
    defaultType = tagType[index];
  }
  return defaultType as PropType<
    EpPropMergeType<
      StringConstructor,
      "primary" | "success" | "info" | "warning" | "danger",
      unknown
    >
  >;
}
</script>

<template>
  <div>
    <template v-for="(item, index) in options">
      <template v-if="values.includes(item.val)">
        <el-tag
          :key="item.val + ''"
          :size="props.size"
          :disable-transitions="true"
          :index="index"
          :type="getTagType(index)"
          effect="dark"
        >
          {{ item.label + "" }}
        </el-tag>
      </template>
    </template>
  </div>
</template>

<style scoped>
.el-tag + .el-tag {
  margin-left: 10px;
}
</style>

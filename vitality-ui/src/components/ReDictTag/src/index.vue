<script setup lang="ts">
import { computed, onMounted, ref } from "vue";
import { isEmpty } from "@pureadmin/utils";
import type { EpPropMergeType } from "element-plus/es/utils/vue/props/types";
import { useDictStoreHook } from "@/store/modules/dict";
import { DictDataVO } from "@/views/vitality/system/dict-data/utils/types";

interface Props {
  /** 字典类型编码 */
  code: string;
  /** 当前字典值 */
  value?: string | Array<String>;
  separator?: string;
  type?: EpPropMergeType<
    StringConstructor,
    "success" | "warning" | "info" | "primary" | "danger",
    unknown
  >;
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
  type: null,
  size: "default"
});

const options = ref<DictDataVO[]>([]);

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

function getTagStyle(dictData: DictDataVO) {
  if (props.type) {
    return props.type;
  }
  return dictData.tagStyle as PropType<
    EpPropMergeType<
      StringConstructor,
      "success" | "warning" | "info" | "primary" | "danger",
      unknown
    >
  >;
}

onMounted(() => {
  options.value = useDictStoreHook().getDicts(props.code);
});
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
          :type="getTagStyle(item)"
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

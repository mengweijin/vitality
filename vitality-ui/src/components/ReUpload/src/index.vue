<script setup lang="ts">
import { ref, computed } from "vue";
import { getToken, formatToken } from "@/utils/auth";
import type { EpPropMergeType } from "element-plus/es/utils/vue/props/types";
import { useRenderIcon } from "@/components/ReIcon/src/hooks";
import Upload from "@iconify-icons/ep/upload";
const { VITE_BASE_API } = import.meta.env;

const uploadAction = VITE_BASE_API + "/system/oss/upload";

interface Props {
  /** 是否支持多个文件上传 */
  multiple?: boolean;
  /** 是否显示已上传文件列表 */
  showFileList?: boolean;
  /** 接受上传的文件类型 */
  accept?: string;
  /** 上传文件按钮大小 */
  size?: EpPropMergeType<
    StringConstructor,
    "" | "default" | "small" | "large",
    unknown
  >;
  flex?: boolean;
}

const headers = ref({
  Authorization: formatToken(getToken())
});

const props = withDefaults(defineProps<Props>(), {
  multiple: false,
  showFileList: false,
  accept: "",
  size: "default",
  flex: false
});

const emit = defineEmits(["on-success"]);

const flexClass = computed(() => {
  return props.flex ? "flex" : "";
});

function onSuccess(response: any) {
  emit("on-success", response);
}
</script>

<template>
  <el-upload
    :action="uploadAction"
    :headers="headers"
    multiple
    :show-file-list="props.showFileList"
    :on-success="onSuccess"
    :class="flexClass"
  >
    <el-button type="primary" :size="props.size" :icon="useRenderIcon(Upload)">
      上传文件
    </el-button>
  </el-upload>
</template>

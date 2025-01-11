<script setup lang="ts">
import "@wangeditor/editor/dist/css/style.css";
import { Editor, Toolbar } from "@wangeditor/editor-for-vue";
import { onBeforeUnmount, ref, shallowRef, onMounted } from "vue";
import { formRules } from "./utils/rule";
import { FormProps, Props } from "./utils/types";
import { formatToken, getToken } from "@/utils/auth";
const { VITE_BASE_API } = import.meta.env;

const uploadAction = VITE_BASE_API + "/system/oss/upload/wang-editor";

const props = withDefaults(defineProps<Props>(), {
  data: () => ({})
});

const formRef = ref();
const row = ref<FormProps>(props.data);

const mode = "default";
// 编辑器实例，必须用 shallowRef
const editorRef = shallowRef();

function getRef() {
  return formRef.value;
}

defineExpose({ getRef });

onMounted(() => {});

const editorConfig = { placeholder: "请输入内容...", MENU_CONF: {} };

// 更多详细配置看 https://www.wangeditor.com/v5/menu-config.html#%E4%B8%8A%E4%BC%A0%E5%9B%BE%E7%89%87
editorConfig.MENU_CONF["uploadImage"] = {
  // 服务端上传地址
  server: uploadAction,
  headers: {
    Authorization: formatToken(getToken())
  },
  // 跨域是否传递 cookie ，默认为 false
  withCredentials: false,
  // 选择文件时的类型限制，根据实际业务改写
  allowedFileTypes: ["image/*"],
  // 最多可上传几个文件，默认为 100
  maxNumberOfFiles: 1,
  // 小于该值就插入 base64 格式（而不上传），默认为 0
  base64LimitSize: 5 * 1024, // 5kb
  // 自定义插入图片
  customInsert(res: any, insertFn) {
    // res 是后端返回的图片地址，根据实际业务改写
    if (res.data.url) {
      setTimeout(() => {
        // insertFn插入图片（链接）进编辑器
        insertFn(VITE_BASE_API + res.data.url);
      }, 2000);
    }
  }
};

const handleCreated = editor => {
  // 记录 editor 实例，重要！
  editorRef.value = editor;
};

// 组件销毁时，也及时销毁编辑器
onBeforeUnmount(() => {
  const editor = editorRef.value;
  if (editor == null) return;
  editor.destroy();
});
</script>

<template>
  <el-form ref="formRef" :model="row" :rules="formRules" label-width="82px">
    <el-form-item v-if="false" label="ID" prop="id">
      <el-input v-model="row.id" clearable disabled />
    </el-form-item>

    <el-form-item label="标题" prop="name">
      <el-input v-model="row.name" clearable placeholder="请输入标题" />
    </el-form-item>

    <el-form-item label="内容">
      <div class="wangeditor">
        <Toolbar
          :editor="editorRef"
          :mode="mode"
          style="border-bottom: 1px solid #ccc"
        />
        <Editor
          v-model="row.description"
          :defaultConfig="editorConfig"
          :mode="mode"
          style="height: 300px; overflow-y: hidden"
          @onCreated="handleCreated"
        />
      </div>
    </el-form-item>
  </el-form>
</template>

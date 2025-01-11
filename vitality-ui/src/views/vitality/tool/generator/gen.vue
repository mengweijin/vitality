<script setup lang="ts">
import { ref, onMounted } from "vue";
import { FormProps, GeneratorArgsBO } from "./utils/types";
import { useRenderIcon } from "@/components/ReIcon/src/hooks";
import Download from "@iconify-icons/ep/download";
import Refresh from "@iconify-icons/ep/refresh";
import {
  generateCode,
  getDefaultArgs,
  getTemplateList,
  downloadCode
} from "@/api/tool/generator";
import tree from "./tree.vue";
import { deviceDetection, downloadByData } from "@pureadmin/utils";
import { handleTree } from "@/utils/tree";
import Prism from "prismjs";

const props = withDefaults(defineProps<FormProps>(), {
  formInline: () => ({})
});

const treeRef = ref();
const treeData = ref([]);
const treeLoading = ref(false);

const loading = ref(false);
const formRef = ref();
const content = ref(
  "👈👈👈----------------\r\n👈👈👈请选择左侧模板\r\n👈👈👈----------------"
);
const newFormInline = ref<GeneratorArgsBO>({});
const resetForm = formEl => {
  if (!formEl) return;
  formEl.resetFields();
};

function onTreeSelect({ id, directory, name, content, selected }) {
  if (!directory) {
    newFormInline.value.templateId = selected ? id : "";
    newFormInline.value.templateName = selected ? name : "";
    newFormInline.value.templateContent = selected ? content : "";
    onExecute();
  }
}

async function onExecute() {
  loading.value = true;
  const r = await generateCode(newFormInline.value);
  content.value = r.data?.content;
  Prism.highlightAll(); // 切换更新内容则重新调用这个方法
  setTimeout(() => {
    loading.value = false;
  }, 500);
}

function onDownload() {
  downloadCode(newFormInline.value).then((data: BlobPart) => {
    downloadByData(data, "generated_codes.zip");
  });
}

onMounted(async () => {
  // 模板树 data
  treeLoading.value = true;
  const data = await getTemplateList();
  treeData.value = handleTree(data);
  treeLoading.value = false;

  newFormInline.value = await getDefaultArgs();
  newFormInline.value.tableName = props.formInline.name;

  Prism.highlightAll();
});
</script>

<template>
  <div
    :class="[
      'flex',
      'justify-between',
      'vtl-generator',
      deviceDetection() && 'flex-wrap'
    ]"
  >
    <tree
      ref="treeRef"
      :class="[
        'mt-5',
        'mr-2',
        'ml-3',
        deviceDetection() ? 'w-full' : 'min-w-[300px]'
      ]"
      :treeData="treeData"
      :treeLoading="treeLoading"
      @tree-select="onTreeSelect"
    />
    <div
      :class="[deviceDetection() ? ['w-full', 'mt-5'] : 'w-[calc(100%-300px)]']"
    >
      <el-form
        ref="formRef"
        :inline="true"
        :model="newFormInline"
        label-width="82px"
        class="search-form bg-bg_color w-[99/100] pl-8 pt-[12px] mt-5 mr-3 overflow-auto"
      >
        <el-form-item label="忽略表前缀" prop="tablePrefix" label-width="auto">
          <el-input
            v-model="newFormInline.tablePrefix"
            clearable
            placeholder=""
            style="width: 120px"
          />
        </el-form-item>
        <el-form-item label="作者" prop="author" label-width="auto">
          <el-input
            v-model="newFormInline.author"
            placeholder=""
            style="width: 120px"
          />
        </el-form-item>
        <el-form-item label="包路径" prop="packages" label-width="auto">
          <el-input
            v-model="newFormInline.packages"
            clearable
            placeholder=""
            style="width: 300px"
          />
        </el-form-item>
        <el-form-item label="模块名" prop="module" label-width="auto">
          <el-input
            v-model="newFormInline.module"
            clearable
            placeholder=""
            style="width: 120px"
          />
        </el-form-item>
        <el-form-item>
          <el-button :icon="useRenderIcon(Refresh)" @click="resetForm(formRef)">
            重置
          </el-button>
          <el-button
            type="primary"
            :icon="useRenderIcon(Download)"
            :loading="loading"
            @click="onDownload"
          >
            下载代码
          </el-button>
        </el-form-item>
      </el-form>

      <div class="mr-3">
        <pre><code class="language-java line-numbers">{{ content }}</code></pre>
      </div>
    </div>
  </div>
</template>

<style scoped lang="scss">
.vtl-generator {
  background: #020409 !important;
}

:deep(.el-dropdown-menu__item i) {
  margin: 0;
}

:deep(.el-button:focus-visible) {
  outline: none;
}

.main-content {
  margin: 24px 24px 0 !important;
}

.search-form {
  :deep(.el-form-item) {
    margin-bottom: 12px;
  }
}
</style>

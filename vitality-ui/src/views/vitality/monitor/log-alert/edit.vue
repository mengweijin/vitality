<script setup lang="ts">
import { ref } from "vue";
import { formRules } from "./utils/rule";
import { FormProps, Props } from "./utils/types";

const props = withDefaults(defineProps<Props>(), {
  data: () => ({})
});

const formRef = ref();
const row = ref<FormProps>(props.data);

function getRef() {
  return formRef.value;
}

defineExpose({ getRef });
</script>

<template>
  <el-form ref="formRef" :model="row" :rules="formRules" label-width="82px">
    <el-form-item label="ID" prop="id">
      <el-input v-model="row.id" clearable placeholder="请输入名称" />
    </el-form-item>
  </el-form>
</template>

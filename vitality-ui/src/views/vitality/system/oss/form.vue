<script setup lang="ts">
import { ref } from "vue";
import { formRules } from "./utils/rule";
import { FormProps } from "./utils/types";

const props = withDefaults(defineProps<FormProps>(), {
  formInline: () => ({})
});

const ruleFormRef = ref();
const newFormInline = ref(props.formInline);

function getRef() {
  return ruleFormRef.value;
}

defineExpose({ getRef });
</script>

<template>
  <el-form
    ref="ruleFormRef"
    :model="newFormInline"
    :rules="formRules"
    label-width="82px"
  >
    <el-form-item label="名称" prop="name">
      <el-input
        v-model="newFormInline.name"
        clearable
        placeholder="请输入名称"
      />
    </el-form-item>
    <el-form-item label="后缀" prop="suffix">
      <el-input
        v-model="newFormInline.suffix"
        clearable
        placeholder="请输入后缀"
      />
    </el-form-item>
    <el-form-item label="存储位置" prop="storagePath">
      <el-input
        v-model="newFormInline.storagePath"
        placeholder="请输入存储位置"
      />
    </el-form-item>
  </el-form>
</template>

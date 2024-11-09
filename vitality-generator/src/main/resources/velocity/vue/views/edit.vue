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
  <el-form-item v-if="false" label="ID" prop="id">
    <el-input v-model="row.id" clearable disabled />
  </el-form-item>

    <el-form-item label="名称" prop="name">
      <el-input v-model="row.name" clearable placeholder="请输入名称" />
    </el-form-item>

    <el-form-item label="排序">
      <el-input-number
        v-model="row.seq"
        class="!w-full"
        :min="0"
        :max="9999"
        controls-position="right"
      />
    </el-form-item>

    <el-form-item label="备注">
      <el-input
        v-model="row.remark"
        placeholder="请输入备注信息"
        type="textarea"
        :rows="3"
      />
    </el-form-item>
  </el-form>
</template>

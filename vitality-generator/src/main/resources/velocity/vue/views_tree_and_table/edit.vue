<script setup lang="ts">
import { ref } from "vue";
import { formRules } from "./utils/rule";
import { Props, UserVO } from "./utils/types";

const props = withDefaults(defineProps<Props>(), {
  data: () => ({}),
  higherOptions: () => []
});

const formRef = ref();
const row = ref<UserVO>(props.data);

function getRef() {
  return formRef.value;
}

defineExpose({ getRef });
</script>

<template>
  <el-form ref="formRef" :model="row" :rules="formRules" label-width="82px">
    <el-form-item label="归属部门">
      <el-cascader
        v-model="row.deptId"
        class="w-full"
        :options="higherOptions"
        :props="{
          value: 'id',
          label: 'name',
          emitPath: false,
          checkStrictly: true
        }"
        clearable
        filterable
        placeholder="请选择归属部门"
      >
        <template #default="{ node, data }">
          <span>{{ data.name }}</span>
          <span v-if="!node.isLeaf"> ({{ data.children.length }}) </span>
        </template>
      </el-cascader>
    </el-form-item>

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

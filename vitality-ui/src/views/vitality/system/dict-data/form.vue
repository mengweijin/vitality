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
    <el-form-item label="字典标签" prop="label">
      <el-input
        v-model="newFormInline.label"
        clearable
        placeholder="请输入字典标签"
      />
    </el-form-item>
    <el-form-item label="字典值" prop="val">
      <el-input
        v-model="newFormInline.val"
        clearable
        placeholder="请输入字典值"
      />
    </el-form-item>

    <el-form-item label="排序">
      <el-input-number
        v-model="newFormInline.seq"
        class="!w-full"
        :min="0"
        :max="9999"
        controls-position="right"
      />
    </el-form-item>
    <el-form-item label="状态">
      <el-switch
        v-model="newFormInline.disabled"
        inline-prompt
        :active-value="'N'"
        :inactive-value="'Y'"
        active-text="启用"
        inactive-text="停用"
        :style="'--el-switch-on-color: #6abe39; --el-switch-off-color: #e84749'"
      />
    </el-form-item>

    <el-form-item label="备注">
      <el-input
        v-model="newFormInline.remark"
        placeholder="请输入备注信息"
        type="textarea"
      />
    </el-form-item>
  </el-form>
</template>

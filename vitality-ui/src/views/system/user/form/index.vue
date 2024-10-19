<script setup lang="ts">
import { ref } from "vue";
import ReCol from "@/components/ReCol";
import { formRules } from "../utils/rule";
import { FormProps } from "../utils/types";
import { usePublicHooks } from "../../../system/hooks";
import { useDictStoreHook } from "@/store/modules/dict";

const props = withDefaults(defineProps<FormProps>(), {
  formInline: () => ({}),
  higherDeptOptions: () => []
});

const ruleFormRef = ref();
const { switchStyle } = usePublicHooks();
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
    <el-row :gutter="30">
      <re-col :value="24" :xs="24" :sm="24">
        <el-form-item label="归属部门">
          <el-cascader
            v-model="newFormInline.deptId"
            class="w-full"
            :options="higherDeptOptions"
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
      </re-col>

      <re-col :value="12" :xs="24" :sm="24">
        <el-form-item label="用户账号" prop="username">
          <el-input
            v-model="newFormInline.username"
            clearable
            placeholder="请输入用户账号"
            :disabled="newFormInline.id !== null"
          />
        </el-form-item>
      </re-col>

      <re-col :value="12" :xs="24" :sm="24">
        <el-form-item label="用户昵称" prop="nickname">
          <el-input
            v-model="newFormInline.nickname"
            clearable
            placeholder="请输入用户昵称"
          />
        </el-form-item>
      </re-col>

      <re-col v-if="newFormInline.id == null" :value="12" :xs="24" :sm="24">
        <el-form-item label="用户密码" prop="password">
          <el-input
            v-model="newFormInline.password"
            clearable
            show-password
            autocomplete="new-password"
            placeholder="请输入用户密码"
            type="password"
          />
        </el-form-item>
      </re-col>
      <re-col :value="12" :xs="24" :sm="24">
        <el-form-item label="手机号" prop="mobile">
          <el-input
            v-model="newFormInline.mobile"
            clearable
            placeholder="请输入手机号"
          />
        </el-form-item>
      </re-col>

      <re-col :value="12" :xs="24" :sm="24">
        <el-form-item label="邮箱" prop="email">
          <el-input
            v-model="newFormInline.email"
            clearable
            placeholder="请输入邮箱"
          />
        </el-form-item>
      </re-col>
      <re-col :value="12" :xs="24" :sm="24">
        <el-form-item label="用户性别">
          <el-select
            v-model="newFormInline.gender"
            placeholder="请选择用户性别"
            class="w-full"
            clearable
          >
            <el-option
              v-for="(item, index) in useDictStoreHook().getDicts(
                'vtl_user_gender'
              )"
              :key="index"
              :label="item.label"
              :value="item.val"
            />
          </el-select>
        </el-form-item>
      </re-col>

      <re-col :value="12" :xs="24" :sm="24">
        <el-form-item label="用户状态">
          <el-switch
            v-model="newFormInline.disabled"
            inline-prompt
            :active-value="'N'"
            :inactive-value="'Y'"
            active-text="启用"
            inactive-text="停用"
            :style="switchStyle"
          />
        </el-form-item>
      </re-col>

      <re-col>
        <el-form-item label="简介/备注">
          <el-input
            v-model="newFormInline.remark"
            placeholder="请输入简介/备注信息"
            type="textarea"
            :rows="4"
          />
        </el-form-item>
      </re-col>
    </el-row>
  </el-form>
</template>

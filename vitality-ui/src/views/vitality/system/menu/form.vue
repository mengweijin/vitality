<script setup lang="ts">
import { ref } from "vue";
import ReCol from "@/components/ReCol";
import { formRules } from "./utils/rule";
import { FormProps } from "./utils/types";
import { transformI18n } from "@/plugins/i18n";
import { IconSelect } from "@/components/ReIcon";
import Segmented from "@/components/ReSegmented";
import ReAnimateSelector from "@/components/ReAnimateSelector";
import {
  menuTypeOptions,
  showLinkOptions,
  fixedTagOptions,
  keepAliveOptions,
  hiddenTagOptions,
  showParentOptions,
  frameLoadingOptions
} from "./utils/enums";

const props = withDefaults(defineProps<FormProps>(), {
  formInline: () => ({}),
  higherMenuOptions: () => []
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
    <el-row :gutter="30">
      <re-col>
        <el-form-item label="菜单类型">
          <el-radio-group v-model="newFormInline.type">
            <el-radio value="MENU" border>菜单</el-radio>
            <el-radio value="IFRAME" border>内嵌页</el-radio>
            <el-radio value="URL" border>外链</el-radio>
            <el-radio value="BTN" border>按钮</el-radio>
          </el-radio-group>
        </el-form-item>
      </re-col>

      <re-col>
        <el-form-item label="上级菜单">
          <el-cascader
            v-model="newFormInline.parentId"
            class="w-full"
            :options="higherMenuOptions"
            :props="{
              value: 'id',
              label: 'title',
              emitPath: false,
              checkStrictly: true
            }"
            clearable
            filterable
            placeholder="请选择上级菜单"
          >
            <template #default="{ node, data }">
              <span>{{ transformI18n(data.title) }}</span>
              <span v-if="!node.isLeaf"> ({{ data.children.length }}) </span>
            </template>
          </el-cascader>
        </el-form-item>
      </re-col>

      <re-col :value="12" :xs="24" :sm="24">
        <el-form-item label="菜单名称" prop="title">
          <el-input
            v-model="newFormInline.title"
            clearable
            placeholder="请输入菜单名称"
          />
        </el-form-item>
      </re-col>
      <re-col v-if="newFormInline.type !== 'BTN'" :value="12" :xs="24" :sm="24">
        <el-form-item label="路由名称" prop="routerName">
          <el-input
            v-model="newFormInline.routerName"
            clearable
            placeholder="请输入路由名称"
          />
        </el-form-item>
      </re-col>

      <re-col v-if="newFormInline.type !== 'BTN'" :value="12" :xs="24" :sm="24">
        <el-form-item label="路由路径" prop="routerPath">
          <el-input
            v-model="newFormInline.routerPath"
            clearable
            placeholder="请输入路由路径"
          />
        </el-form-item>
      </re-col>
      <re-col
        v-show="newFormInline.type === 'MENU'"
        :value="12"
        :xs="24"
        :sm="24"
      >
        <el-form-item label="组件路径">
          <el-input
            v-model="newFormInline.componentPath"
            clearable
            placeholder="请输入组件路径"
          />
        </el-form-item>
      </re-col>

      <re-col :value="12" :xs="24" :sm="24">
        <el-form-item label="菜单排序">
          <el-input-number
            v-model="newFormInline.seq"
            class="!w-full"
            :min="1"
            :max="9999"
            controls-position="right"
          />
        </el-form-item>
      </re-col>
      <re-col
        v-show="newFormInline.type === 'MENU'"
        :value="12"
        :xs="24"
        :sm="24"
      >
        <el-form-item label="路由重定向">
          <el-input
            v-model="newFormInline.redirect"
            clearable
            placeholder="请输入默认跳转地址"
          />
        </el-form-item>
      </re-col>

      <re-col
        v-show="newFormInline.type !== 'BTN'"
        :value="12"
        :xs="24"
        :sm="24"
      >
        <el-form-item label="菜单图标">
          <IconSelect v-model="newFormInline.icon" class="w-full" />
        </el-form-item>
      </re-col>
      <re-col
        v-show="newFormInline.type !== 'BTN'"
        :value="12"
        :xs="24"
        :sm="24"
      >
        <el-form-item label="右侧图标">
          <el-input
            v-model="newFormInline.extraIcon"
            clearable
            placeholder="菜单名称右侧的额外图标"
          />
        </el-form-item>
      </re-col>

      <re-col
        v-show="
          newFormInline.type === 'MENU' || newFormInline.type === 'IFRAME'
        "
        :value="12"
        :xs="24"
        :sm="24"
      >
        <el-form-item label="进场动画">
          <ReAnimateSelector
            v-model="newFormInline.enterTransition"
            placeholder="请选择页面进场加载动画"
          />
        </el-form-item>
      </re-col>
      <re-col
        v-show="
          newFormInline.type === 'MENU' || newFormInline.type === 'IFRAME'
        "
        :value="12"
        :xs="24"
        :sm="24"
      >
        <el-form-item label="离场动画">
          <ReAnimateSelector
            v-model="newFormInline.leaveTransition"
            placeholder="请选择页面离场加载动画"
          />
        </el-form-item>
      </re-col>

      <re-col
        v-show="newFormInline.type === 'MENU'"
        :value="12"
        :xs="24"
        :sm="24"
      >
        <el-form-item label="菜单激活">
          <el-input
            v-model="newFormInline.activePath"
            clearable
            placeholder="请输入需要激活的菜单"
          />
        </el-form-item>
      </re-col>
      <re-col
        v-if="newFormInline.type === 'MENU'"
        :value="12"
        :xs="24"
        :sm="24"
      >
        <!-- 按钮级别权限设置 -->
        <el-form-item label="权限标识" prop="permission">
          <el-input
            v-model="newFormInline.permission"
            clearable
            placeholder="请输入权限标识"
          />
        </el-form-item>
      </re-col>

      <re-col
        v-show="newFormInline.type === 'IFRAME'"
        :value="12"
        :xs="24"
        :sm="24"
      >
        <!-- iframe -->
        <el-form-item label="链接地址">
          <el-input
            v-model="newFormInline.iframeSrc"
            clearable
            placeholder="请输入 iframe 链接地址"
          />
        </el-form-item>
      </re-col>
      <re-col
        v-if="newFormInline.type === 'IFRAME'"
        :value="12"
        :xs="24"
        :sm="24"
      >
        <el-form-item label="加载动画">
          <Segmented
            :modelValue="newFormInline.iframeLoading === 'Y' ? 0 : 1"
            :options="frameLoadingOptions"
            @change="
              ({ option: { value } }) => {
                newFormInline.iframeLoading = value ? 'Y' : 'N';
              }
            "
          />
        </el-form-item>
      </re-col>

      <re-col
        v-show="newFormInline.type !== 'BTN'"
        :value="12"
        :xs="24"
        :sm="24"
      >
        <el-form-item label="菜单">
          <Segmented
            :modelValue="newFormInline.showLink === 'Y' ? 0 : 1"
            :options="showLinkOptions"
            @change="
              ({ option: { value } }) => {
                newFormInline.showLink = value ? 'Y' : 'N';
              }
            "
          />
        </el-form-item>
      </re-col>
      <re-col
        v-show="newFormInline.type !== 'BTN'"
        :value="12"
        :xs="24"
        :sm="24"
      >
        <el-form-item label="父级菜单">
          <Segmented
            :modelValue="newFormInline.showParent === 'Y' ? 0 : 1"
            :options="showParentOptions"
            @change="
              ({ option: { value } }) => {
                newFormInline.showParent = value ? 'Y' : 'N';
              }
            "
          />
        </el-form-item>
      </re-col>

      <re-col
        v-show="
          newFormInline.type === 'MENU' || newFormInline.type === 'IFRAME'
        "
        :value="12"
        :xs="24"
        :sm="24"
      >
        <el-form-item label="缓存页面">
          <Segmented
            :modelValue="newFormInline.keepAlive === 'Y' ? 0 : 1"
            :options="keepAliveOptions"
            @change="
              ({ option: { value } }) => {
                newFormInline.keepAlive = value ? 'Y' : 'N';
              }
            "
          />
        </el-form-item>
      </re-col>

      <re-col
        v-show="
          newFormInline.type === 'MENU' || newFormInline.type === 'IFRAME'
        "
        :value="12"
        :xs="24"
        :sm="24"
      >
        <el-form-item label="标签页">
          <Segmented
            :modelValue="newFormInline.hiddenTag === 'N' ? 0 : 1"
            :options="hiddenTagOptions"
            @change="
              ({ option: { value } }) => {
                newFormInline.hiddenTag = value ? 'Y' : 'N';
              }
            "
          />
        </el-form-item>
      </re-col>
      <re-col
        v-show="
          newFormInline.type === 'MENU' || newFormInline.type === 'IFRAME'
        "
        :value="12"
        :xs="24"
        :sm="24"
      >
        <el-form-item label="固定标签页">
          <Segmented
            :modelValue="newFormInline.fixedTag === 'Y' ? 0 : 1"
            :options="fixedTagOptions"
            @change="
              ({ option: { value } }) => {
                newFormInline.fixedTag = value ? 'Y' : 'N';
              }
            "
          />
        </el-form-item>
      </re-col>
    </el-row>
  </el-form>
</template>

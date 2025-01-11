<script setup lang="ts">
import { onMounted, ref } from "vue";
import { deviceDetection } from "@pureadmin/utils";
import {
  getUserPreference,
  saveUserPreference,
  type UserPreferenceVO
} from "@/api/system/user-preference";

defineOptions({
  name: "Preferences"
});

const userPreference = ref<UserPreferenceVO>(null);

const list = ref([
  {
    prop: "userMessage",
    title: "用户消息",
    illustrate: "其他用户的消息将以站内信的形式通知",
    checked: true
  },
  {
    prop: "systemMessage",
    title: "系统消息",
    illustrate: "系统消息将以站内信的形式通知",
    checked: true
  },
  {
    prop: "todoTask",
    title: "待办任务",
    illustrate: "待办任务将以站内信的形式通知",
    checked: true
  }
]);

function getCheckedValue(prop: string) {
  if (!userPreference.value) {
    return true;
  }
  let val = userPreference.value[prop];
  return !val || val === "Y";
}

function onChange(val, item) {
  saveUserPreference({ [item.prop]: val === false ? "N" : "Y" });
}

onMounted(async () => {
  userPreference.value = await getUserPreference();
  for (let i in list.value) {
    list.value[i]["checked"] = getCheckedValue(list.value[i]["prop"]);
  }
});
</script>

<template>
  <div
    :class="[
      'min-w-[180px]',
      deviceDetection() ? 'max-w-[100%]' : 'max-w-[70%]'
    ]"
  >
    <h3 class="my-8">偏好设置</h3>
    <div v-for="(item, index) in list" :key="index">
      <div class="flex items-center">
        <div class="flex-1">
          <p>{{ item.title }}</p>
          <p class="wp-4">
            <el-text class="mx-1" type="info">
              {{ item.illustrate }}
            </el-text>
          </p>
        </div>
        <el-switch
          v-model="item.checked"
          inline-prompt
          active-text="是"
          inactive-text="否"
          @change="val => onChange(val, item)"
        />
      </div>
      <el-divider />
    </div>
  </div>
</template>

<style lang="scss" scoped>
.el-divider--horizontal {
  border-top: 0.1px var(--el-border-color) var(--el-border-style);
}
</style>

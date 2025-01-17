<script setup lang="ts">
import { onMounted, ref } from "vue";
import { message } from "@/utils/message";
import { deviceDetection } from "@pureadmin/utils";
import { getSensitiveMine } from "@/api/system/user";
import { useDictStoreHook } from "@/store/modules/dict";

defineOptions({
  name: "AccountManagement"
});

const userInfo = ref(null);

const list = ref([
  {
    prop: "passwordLevel",
    title: "账户密码",
    prefix: "密码强度：",
    illustrate: "一般",
    button: "修改"
  },
  {
    prop: "mobile",
    title: "绑定手机",
    prefix: "已绑定手机：",
    illustrate: "未绑定",
    button: "修改"
  },
  {
    prop: "email",
    title: "绑定邮箱",
    prefix: "已绑定邮箱：",
    illustrate: "未绑定",
    button: "修改"
  },
  {
    prop: "totp",
    title: "动态口令（TOTP）",
    prefix: "绑定动态口令：",
    illustrate: "未绑定",
    button: "修改"
  }
]);

function onClick(item) {
  console.log("onClick", item.title);
  message("请根据具体业务自行实现", { type: "success" });
}

function getIllustrateValue(prop: string) {
  let val = userInfo.value[prop];
  if (prop === "passwordLevel") {
    let dict = useDictStoreHook().getDictByCodeAndVal(
      "vtl_password_level",
      val
    );
    return dict?.label;
  } else if (prop === "totp") {
    return val ? "已绑定" : "未绑定";
  } else {
    return val;
  }
}

onMounted(async () => {
  userInfo.value = await getSensitiveMine();
  for (let i in list.value) {
    list.value[i]["illustrate"] =
      list.value[i]["prefix"] + getIllustrateValue(list.value[i]["prop"]);
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
    <h3 class="my-8">账户管理</h3>
    <div v-for="(item, index) in list" :key="index">
      <div class="flex items-center">
        <div class="flex-1">
          <p>{{ item.title }}</p>
          <el-text class="mx-1" type="info">{{ item.illustrate }}</el-text>
        </div>
        <el-button type="primary" text @click="onClick(item)">
          {{ item.button }}
        </el-button>
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

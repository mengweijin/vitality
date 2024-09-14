<script lang="ts" setup>
import { getConfig } from "@/config";
import { ref } from "vue";

const fullYear = ref(new Date().getFullYear());
const TITLE = getConfig("Title");
</script>

<template>
  <footer
    class="layout-footer text-[rgba(0,0,0,0.6)] dark:text-[rgba(220,220,242,0.8)]"
  >
    <a class="a" href="https://vitality.aday.fun" target="_blank">{{
      TITLE
    }}</a>
    &nbsp;|&nbsp;
    <a class="a" href="https://home.aday.fun" target="_blank"
      >https://home.aday.fun</a
    >
    &nbsp;&nbsp;
    <a
      class="a"
      href="https://beian.miit.gov.cn/#/Integrated/lawStatute"
      target="_blank"
      >陕ICP备2022013125号</a
    >
    &nbsp;| Copyright © 2018 ~ {{ fullYear }} | Meng Wei Jin | MIT LICENSED
  </footer>
</template>

<style lang="scss" scoped>
.layout-footer {
  display: flex;
  align-items: center;
  justify-content: center;
  width: 100%;
  padding: 0 0 8px;
  font-size: 14px;
}
.a {
  text-decoration: underline;
}
.a:hover {
  color: red;
}
</style>

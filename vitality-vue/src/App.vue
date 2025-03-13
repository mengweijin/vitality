<script setup>
import HelloWorld from './components/HelloWorld.vue';

import { LoginBackgroundIcon } from '@utils/svg-icons.js';

function getFavicon() {
  return new URL("/favicon.svg", import.meta.url).href;
}
function getLogo() {
  return new URL("/logo.svg", import.meta.url).href;
}
</script>

<template>
  <el-container>
      <el-header>Header</el-header>
      <el-container>
        <el-aside width="200px">Aside</el-aside>
        <el-container>
          <el-main>Main
            <div>
            <a href="https://vite.dev" target="_blank">
              <img :src="getFavicon()" class="logo" alt="Vite logo" />
            </a>
            <a href="https://vuejs.org/" target="_blank">
              <img :src="getLogo()" class="logo vue" alt="Vue logo" />
            </a>
          </div>
          <HelloWorld msg="Vite + Vue" />
          </el-main>
          <el-footer>Footer</el-footer>
        </el-container>
      </el-container>
    </el-container>
</template>

<style scoped>
.logo {
  height: 6em;
  padding: 1.5em;
  will-change: filter;
  transition: filter 300ms;
}

.logo:hover {
  filter: drop-shadow(0 0 2em #646cffaa);
}

.logo.vue:hover {
  filter: drop-shadow(0 0 2em #42b883aa);
}
</style>

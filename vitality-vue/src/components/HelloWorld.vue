<script setup>
import { ref } from 'vue';
import { useDark, useToggle } from '@vueuse/core';

const props = defineProps({
  msg: String,
})

const count = ref(0);

const isDark = useDark();
console.log(isDark);
const toggleDark = useToggle(isDark);

</script>

<template>
  <div>
    <h1>{{ msg }}</h1>



    <el-button type="primary" @click="toggleDark()">
      <i inline-block align-middle i="dark:carbon-moon carbon-sun" />
      <span class="ml-2">{{ isDark ? 'Dark' : 'Light' }}</span>
    </el-button>


    <el-button type="primary">element-plus button</el-button>

    <div class="card">
      <button type="button" @click="count++">count is {{ count }}</button>
      <p>
        Edit
        <code>components/HelloWorld.vue</code> to test HMR
      </p>
    </div>

    <p>
      Check out
      <a href="https://vuejs.org/guide/quick-start.html#local" target="_blank">create-vue</a>, the official Vue + Vite
      starter
    </p>
    <p>
      Learn more about IDE Support for Vue in the
      <a href="https://vuejs.org/guide/scaling-up/tooling.html#ide-support" target="_blank">Vue Docs Scaling up
        Guide</a>.
    </p>
    <p>Click on the Vite and Vue logos to learn more</p>
  </div>

</template>

<style scoped></style>

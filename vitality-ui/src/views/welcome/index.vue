<script setup lang="ts">
import avatar from "@/assets/login/avatar.svg?component";

defineOptions({
  name: "Welcome"
});
</script>

<template>
  <div class="container">
    <div class="picture">
      <avatar class="avatar" />
    </div>
  </div>
</template>

<style scoped>
.container {
  height: 100%;
}
.picture {
  width: 100%;
  height: 80%;
  display: flex;
  align-items: center;
  justify-content: center;
}
.avatar {
  width: 40%;
  height: 40%;
}
</style>

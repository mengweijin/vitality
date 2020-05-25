<template>
  <el-menu router :default-active="activeIndex" background-color="#393D49" active-text-color="#FFB800" text-color="#ffffff" mode="horizontal" @select="handleSelect">
    <el-menu-item index="/index">
      <svg class="icon" aria-hidden="true" font-size="30px"><use xlink:href="#iconzhuye"></use></svg>
    </el-menu-item>
    <el-menu-item index="/video-downloader/task">小视频下载任务</el-menu-item>
    <el-submenu index="2" disabled>
      <template slot="title">我的工作台</template>
      <el-menu-item index="2-1">选项1</el-menu-item>
      <el-menu-item index="2-2">选项2</el-menu-item>
      <el-menu-item index="2-3">选项3</el-menu-item>
      <el-submenu index="2-4">
        <template slot="title">选项4</template>
        <el-menu-item index="2-4-1">选项1</el-menu-item>
        <el-menu-item index="2-4-2">选项2</el-menu-item>
        <el-menu-item index="2-4-3">选项3</el-menu-item>
      </el-submenu>
    </el-submenu>
    <el-menu-item index="3" disabled>消息中心</el-menu-item>
    <el-menu-item index="4" disabled><a href="https://www.ele.me" target="_blank">订单管理</a></el-menu-item>
  </el-menu>
</template>

<script type="text/ecmascript-6">
  export default {
    data: function() {   
      return {
        activeIndex: undefined
      }
    },
    watch: {
      
    },
    methods:{
      handleSelect(index,  indexPath) {
        this.activeIndex = index
      }
    }
  }
</script>

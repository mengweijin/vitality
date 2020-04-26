<template>
  <div>
    <router-view name="mengweijinHeader"/>

    <el-container>

      <el-header>
        <router-view name="header"/>
      </el-header>

      <el-main style="margin-bottom: 60px">
        <router-view/>
      </el-main>

      <el-footer>
        <router-view name="footer"/>
      </el-footer>

    </el-container>
  </div>
</template>

<script type="text/ecmascript-6">
  export default {
    data: function() {   
      return {
        
      }
    },
    watch: {
      
    },
    methods:{
      
    },
    created: function() {
    }
  }
</script>
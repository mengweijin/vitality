<template>
  <div>
    <router-view name="mengweijinHeader"/>

    <el-container>

      <el-header style="line-height:44px; height:44px;">
        
      </el-header>

      <el-main>
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
        this.$router.push("/2/play/" + this.$route.params.taskId)
    }
  }
</script>
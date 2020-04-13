<template>
  <div style="width:60%; margin:0 auto;">
    <el-row style="height:40px; lineHeight:40px; color:red">
      <el-col :span="6">
        {{this.$route.params.taskId}}
      </el-col>
      <el-col :span="18" v-text="attachmentName">
        
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="24">
        <video-player class="vjs-big-play-centered" :options="videoOptions"></video-player>
      </el-col>
    </el-row> 
  </div>
</template>

<script type="text/ecmascript-6">
  export default {
    data () {
      return {
        task: undefined,
        videoOptions : {
          autoplay: true,
          controls: true,
          preload: 'auto',
          fluid: true,
          playbackRates: [0.5, 1, 1.5, 2],
          aspectRatio: '16:9',
          sources: [
            {
              src: this.$axios.defaults.baseURL + '/player/' + this.$route.params.taskId,
              type: "video/mp4"
            }
          ]
        }
      }
    },
    computed: {
      attachmentName: function() {
        if(this.task != undefined){
          return this.task.attachmentName;
        }
        
      }
    },
    methods: {
      queryTask() {
      let _this = this;
      this.$get('/task/' + this.$route.params.taskId)
      .then(function (response) {
          _this.task = response
      })
      }
    },
    created() {
      this.queryTask()
    }
  }
</script>

<style>
/* 
 * video.js 在未播放时，会显示一个大的播放按钮。在视频暂停时也显示这个播放按钮 
 */
.vjs-paused .vjs-big-play-button,
.vjs-paused.vjs-has-started .vjs-big-play-button {
    display: block;
}

/* 
 * video.js 默认的播放按钮是圆角矩形, 修改播放按钮为圆形
 */
 .video-js .vjs-big-play-button{
    font-size: 2.5em;
    line-height: 2.3em;
    height: 2.5em;
    width: 2.5em;
    -webkit-border-radius: 2.5em;
    -moz-border-radius: 2.5em;
    border-radius: 2.5em;
    background-color: #73859f;
    background-color: rgba(115,133,159,.5);
    border-width: 0.15em;
    margin-top: -1.25em;
    margin-left: -1.75em;
}
/* 中间的播放箭头 */
.vjs-big-play-button .vjs-icon-placeholder {
    font-size: 1.63em;
}
/* 加载圆圈 */
.vjs-loading-spinner {
    font-size: 2.5em;
    width: 2em;
    height: 2em;
    border-radius: 1em;
    margin-top: -1em;
    margin-left: -1.5em;
}

/* 
 * 进度显示当前播放时间
 */
.video-js .vjs-time-control{display:block;}
.video-js .vjs-remaining-time{display: none;}

/** 页面背景 */
.mengweijin-video-background {
  background-color:black;
  background-image:
  radial-gradient(white, rgba(255,255,255,.2) 2px, transparent 40px),
  radial-gradient(white, rgba(255,255,255,.15) 1px, transparent 30px),
  radial-gradient(white, rgba(255,255,255,.1) 2px, transparent 40px),
  radial-gradient(rgba(255,255,255,.4), rgba(255,255,255,.1) 2px, transparent 30px);
  background-size: 550px 550px, 350px 350px, 250px 250px, 150px 150px; 
  background-position: 0 0, 40px 60px, 130px 270px, 70px 100px;
}
</style>
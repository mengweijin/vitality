/**
 * 导入自定义的模块到 Layui 中。
 * 后面就可以通过下面的方式来使用：
 * layui.use(['模块名'], function(){
 *   var 模块名 = layui.模块名
 * })
 */
layui.config({
    base: '/js/layui-extend/'
}).extend({
    quickboot: 'quickboot'
}).use(['quickboot'], function(){
    //var quickboot = layui.quickboot;
    //console.log(quickboot)
});



layui.use(['jquery', 'layer'], function(){
  var $ = layui.$, layer = layui.layer
  /**
   * jquery ajax 全局配置
   */
  $.ajaxSetup({
      layerIndex: -1,
      cache: false,
      beforeSend: function(xhr) {
          this.layerIndex = layer.load(2, { shade: [0.5, '#393D49'] });
          var csrfHeader = $("meta[name='_csrf_header']").attr("content");
          var csrf = $("meta[name='_csrf']").attr("content");
          if(csrfHeader && csrf) {
              xhr.setRequestHeader(header, csrf);
          }
      },
      complete: function (xhr) {
          layer.close(this.layerIndex);
      },
      error : function(xhr, textStatus, errorThrown) {
          switch (xhr.status) {
              case (500):
                  layer.msg("Server Exception!", {icon: 2});
                  break;
              case (401):
                  layer.msg("No login or Session is invalid!", {icon: 2});
                  // 刷新当前页面
                  window.location.reload();
                  break;
              case (403):
                  layer.msg("No permission!", {icon: 2});
                  break;
              case (408):
                  layer.msg("Request timeout!", {icon: 2});
                  break;
              default:
                  layer.msg('Unknown error, please contact your administrator!', {icon: 2});
                  break;
          }
      }
  });
});

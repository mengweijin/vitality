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

    /**
     * put 请求方法。jquery 默认只有 get/post 两种快捷的方法，这里扩展一下。
     * 注意：jquery 中的 get/post 方法参数都一样（url, data, success），所以用法也一样。
     * 但是 axios 中的 get 方法的使用就有区别于其他方法，这点需要注意。
     * @param {String} url
     * @param {Object} data
     * @param {Function} success http 请求成功时的回调函数。
     */
    $.put = function(url, data, success){
        $.ajax({
            url: url,
            method: "PUT", // version added: 1.9.0
            type: "PUT", // prior to 1.9.0.
            contentType: "application/json",
            dataType: "json",
            data: data,
            success:function(result){
                success(result);
            }
        });
    }

    /**
     * del 请求方法（delete 是关键词，故使用 del 名称。）。
     * @param {String} url
     * @param {Object} data
     * @param {Function} success http 请求成功时的回调函数。
     */
    $.del = function(url, data, success){
        $.ajax({
            url: url,
            method: "DELETE", // version added: 1.9.0
            type: "DELETE", // prior to 1.9.0.
            contentType: "application/json",
            dataType: "json",
            data: data,
            success:function(result){
                success(result);
            }
        });
    }
});

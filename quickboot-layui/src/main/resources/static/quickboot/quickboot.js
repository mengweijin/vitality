/**
 * 导入自定义的模块到 Layui 中。
 * 后面就可以通过下面的方式来使用：
 * layui.use(['模块名'], function(){
 *   var 模块名 = layui.模块名
 * })
 */
layui.config({
    base: '/quickboot/layui-extend/'
}).extend({
    // 别名，需要跟文件名保持一致，且不能加文件后缀 .js
    quickboot: 'quickboot-layui'
    // 相对于上述 base 目录的子目录
    //mod1: 'admin/mod1'
}).use(['quickboot'], function () {
    var qb = layui.quickboot;
    //console.log(qb)
});

layui.use(['jquery', 'layer'], function () {
    let $ = layui.$, jQuery = layui.jquery, layer = layui.layer
    /**
    * jquery ajax 全局配置
    */
    $.ajaxSetup({
        layerIndex: -1,
        cache: false,
        //contentType: 'application/json;charset=UTF-8', // default is: application/x-www-form-urlencoded
        //data: JSON.stringify(data) // contentType 为 json, 就要这样处理，太麻烦了，所以还是不要用了。使用 application/x-www-form-urlencoded 只需要 Controller 请求方法参数不要添加 @RequestBody 注解就行。
        beforeSend: function (xhr) {
            this.layerIndex = layer.load(2, { shade: [0.5, '#393D49'] });
            var csrfHeader = $("meta[name='_csrf_header']").attr("content");
            var csrf = $("meta[name='_csrf']").attr("content");
            if (csrfHeader && csrf) {
                xhr.setRequestHeader(header, csrf);
            }
        },
        complete: function (xhr) {
            layer.close(this.layerIndex);
            if(this.type != 'GET') {
                layer.msg('操作成功！ <br>Operate successfully! ', {icon: 1});
            }
        },
        error: function (xhr, textStatus, errorThrown) {
            switch (xhr.status) {
                case (400):
                    var message = "客户端异常，请检查你的输入！<br>Client Error! Please check your input!"
                    if (xhr.responseJSON.message) {
                        message = xhr.responseJSON.message;
                    }
                    layer.msg(message, { icon: 5, time: 0, closeBtn: 1 });
                    break;
                case (401):
                    layer.msg("未登录或者会话已过期！<br>No login or Session is invalid!", { icon: 2 });
                    // 刷新当前页面
                    top.location.reload(true);
                    break;
                case (403):
                    layer.msg("无权限！<br>No permission!", { icon: 2 });
                    break;
                case (408):
                    layer.msg("请求超时！<br>Request timeout!", { icon: 2 });
                    break;
                case (500):
                    layer.msg("服务器异常！<br>Server Exception!", { icon: 2 });
                    break;
                default:
                    layer.msg('未知异常，请联系系统管理员！<br>Unknown error, please contact your administrator!', { icon: 2 });
                    break;
            }
        }
    });

    /**
     * 参考自 jquery 源码第 9879 行左右，基本只修改了 get/post 为 put/del
     * jQuery.each( [ "get", "post" ], function( i, method )
     * jquery 默认只有 get/post 两种快捷的方法，这里扩展一下。
     * 注意：axios 中的 get 方法的使用就有区别于其他方法，这点需要注意。
     * put 请求方法。delete 请求方法。
     */
    jQuery.each(["put", "delete"], function (i, method) {
        /**
         * 参数说明。和 jquery 中的 get/post 方法参数都一样（url, data, callback, type），所以用法也一样。
         * @param {String} url
         * @param {Object} data
         * @param {Function} callback http 请求成功时的回调函数。
         * @param {String} type 可选。规定预期的服务器响应的数据类型。默认执行智能判断（xml、json、script 或 html）。
         */
        jQuery[method] = function (url, data, callback, type) {
            // shift arguments if data argument was omitted
            if (jQuery.isFunction(data)) {
                type = type || callback;
                callback = data;
                data = undefined;
            }
            // The url can be an options object (which then must have .url)
            return jQuery.ajax(jQuery.extend({
                url: url,
                method: method, // alias for type. version added: 1.9.0
                type: method,  // prior to 1.9.0.
                dataType: type,
                data: data,
                success: callback
            }, jQuery.isPlainObject(url) && url));
        };
    });
});

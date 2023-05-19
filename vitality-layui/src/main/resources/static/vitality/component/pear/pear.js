window.rootPath = (function (src) {
	src = document.currentScript
		? document.currentScript.src
		: document.scripts[document.scripts.length - 1].src;
	return src.substring(0, src.lastIndexOf("/") + 1);
})();

layui.config({
	base: rootPath + "module/",
	version: "3.10.0"
}).extend({
	admin: "admin", 	// 框架布局组件
	menu: "menu",		// 数据菜单组件
	frame: "frame", 	// 内容页面组件
	tab: "tab",			// 多选项卡组件
	echarts: "echarts", // 数据图表组件
	echartsTheme: "echartsTheme", // 数据图表主题
	encrypt: "encrypt",		// 数据加密组件
	select: "select",	// 下拉多选组件
	drawer: "drawer",	// 抽屉弹层组件
	notice: "notice",	// 消息提示组件
	step:"step",		// 分布表单组件
	tag:"tag",			// 多标签页组件
	popup:"popup",      // 弹层封装
	treetable:"treetable",   // 树状表格
	dtree:"dtree",			// 树结构
	tinymce:"tinymce/tinymce", // 编辑器
	area:"area",			// 省市级联  
	count:"count",			// 数字滚动
	topBar: "topBar",		// 置顶组件
	button: "button",		// 加载按钮
	design: "design",		// 表单设计
	card: "card",			// 数据卡片组件
	loading: "loading",		// 加载组件
	cropper:"cropper",		// 裁剪组件
	convert:"convert",		// 数据转换
	yaml:"yaml",			// yaml 解析组件
	context: "context",		// 上下文组件
	http: "http",			// ajax请求组件
	theme: "theme",			// 主题转换
	message: "message",     // 通知组件
	toast: "toast",         // 消息通知
	iconPicker: "iconPicker",// 图标选择
	nprogress: "nprogress",  // 进度过渡
	watermark:"watermark/watermark", //水印
	fullscreen:"fullscreen",  //全屏组件
	popover:"popover/popover" //汽泡组件
}).use(['layer', 'theme'], function () {
	layui.theme.changeTheme(window, false);
});


layui.use(['jquery'], function () {
    let $ = layui.$, jQuery = layui.jquery;
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

    /**
    * jquery ajax 全局配置
    */
    $.ajaxSetup({
        layerIndex: -1,
        cache: false,
        //contentType: 'application/json;charset=UTF-8', // default is: application/x-www-form-urlencoded
        //data: JSON.stringify(data) // contentType 为 json, 就要这样处理，太麻烦了，所以还是不要用了。使用 application/x-www-form-urlencoded 只需要 Controller 请求方法参数不要添加 @RequestBody 注解就行。
        beforeSend: function (xhr) {
            this.layerIndex = layui.layer.load(2, { shade: [0.5, '#393D49'] });
            var csrfHeader = $("meta[name='_csrf_header']").attr("content");
            var csrf = $("meta[name='_csrf']").attr("content");
            if (csrfHeader && csrf) {
                xhr.setRequestHeader(header, csrf);
            }
            //if(this.data) {
            //    this.data = JSON.stringify(this.data);
            //}
        },
        complete: function (xhr) {
            layui.layer.close(this.layerIndex);
            if (this.type != 'GET' && xhr.status == 200) {
                layui.layer.msg('操作成功！Operate successfully!', { icon: 1 });
            }
        },
        error: function (xhr, textStatus, errorThrown) {
            switch (xhr.status) {
                case (400):
                    var message = "客户端异常，请检查你的输入！<br>Client Error! Please check your input!"
                    if (xhr.responseJSON.message) {
                        message = xhr.responseJSON.message;
                    }
                    layui.layer.msg(message, { icon: 5, time: 0, closeBtn: 1, title: xhr.status });
                    break;
                case (401):
                    layui.layer.msg("未登录或者会话已过期！<br>No login or Session is invalid!", { icon: 2, title: xhr.status });
                    // 刷新当前页面
                    top.location.reload(true);
                    break;
                case (403):
                    layui.layer.msg("无权限！No permission!", { icon: 2, title: xhr.status });
                    break;
                case (404):
                    layui.layer.msg("找不到资源！Not Found!", { icon: 2, title: xhr.status });
                    break;
                case (408):
                    layui.layer.msg("请求超时！Request timeout!", { icon: 2, title: xhr.status });
                    break;
                case (500):
                    layui.layer.msg("服务器异常！Server Exception!", { icon: 2, title: xhr.status });
                    break;
                default:
                    layui.layer.msg('未知异常，请联系系统管理员！<br>Unknown error, please contact your administrator!', { icon: 2, title: xhr.status });
                    break;
            }
        }
    });

});
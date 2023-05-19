layui.define(['jquery'], function (exports) {
    "use strict";

    /**
     * 常用封装类
     * */
    var MOD_NAME = 'vitality',
        $ = layui.jquery,
        jQuery = layui.jquery;

    var vitality = new function () {

        this.copyToClip = function(text) {
            let el = document.createElement('textarea')
            el.value = text
            document.body.appendChild(el)
            // 选中input元素的文本
            el.select()
            // 复制内容到剪贴板
            document.execCommand('copy')
            document.body.removeChild(el)
            layer.msg("Copy SUCCESS!")
        }

        /**
         * 获取 Layui checkbox 的提交值。
         * selector: 如：
         * 根据 lay-filter 和 checkbox 的 name 属性选择：'[lay-filter=operate-filter] input:checkbox[name=hobby]:checked'
         * 根据 formId 和 checkbox 的 name 属性选择：'form input:checkbox[name=hobby]:checked'
         * @return 示例：[1,2,3,4]
         */
         this.checkBoxCheckedValue = function (selector) {
            let value = [];
            $(selector).each(function () {
                value.push($(this).val());
            });
            // return value.join(",");
            return value;
        }

        /**
         * 判断是否是空
         * @param value
         */
         this.isEmpty = function(value) {
            if(value == null || undefined) {
                return true;
            } else {
                value = value.replace(/\s/g, "");
                return value == "" || value == "undefined" || value == "null";
            }
        }

        this.getQueryVariable = function (variable) {
            if(variable) {
                let reg = new RegExp("(^|&)" + variable + "=([^&]*)(&|$)");
                let matchedArray = decodeURI(window.location.search.substring(1)).match(reg);
                return matchedArray == null ? null : decodeURIComponent(matchedArray[2]);
            } else {
                let obj = {};
                let splitArray = decodeURI(window.location.search.substring(1)).split('&');
                for(let i in splitArray) {
                    let variableArray = splitArray[i].split('=');
                    obj[variableArray[0]] = decodeURIComponent(variableArray[1]);
                }
                return obj;
            }
        }

        this.buildQueryVariable = function (data) {
            let url = '';
            for(let i in data) {
                url += '&' + i + '=' + encodeURIComponent(data[i]);
            }
            return url.substring(1);
        }

        /**
         * 参考自 jquery 源码第 9879 行左右，基本只修改了 get/post 为 get/post/put/del
         * jQuery.each( [ "get", "post" ], function( i, method )
         * jquery 默认只有 get/post 两种快捷的方法，这里扩展一下。
         * 注意：axios 中的 get 方法的使用就有区别于其他方法，这点需要注意。
         * put 请求方法。delete 请求方法。
         */
        jQuery.each(["get", "post", "put", "delete"], function (i, method) {
            /**
             * 参数说明。和 jquery 中的 get/post 方法参数都一样（url, data, callback, type），所以用法也一样。
             * @param {String} url
             * @param {Object} data
             * @param {Function} callback http 请求成功时的回调函数。
             * @param {String} type 可选。规定预期的服务器响应的数据类型。默认执行智能判断（xml、json、script 或 html）。
             */
            this.ajax[method] = function (url, data, callback, type) {
                // shift arguments if data argument was omitted
                if (jQuery.isFunction(data)) {
                    type = type || callback;
                    callback = data;
                    data = undefined;
                }
                // The url can be an options object (which then must have .url)
                return jQuery.ajax(jQuery.extend({
                    layerIndex: -1,
                    url: url,
                    cache: false,
                    //contentType: 'application/json;charset=UTF-8', // default is: application/x-www-form-urlencoded
                    //data: JSON.stringify(data) // contentType 为 json, 就要这样处理，太麻烦了，所以还是不要用了。使用 application/x-www-form-urlencoded 只需要 Controller 请求方法参数不要添加 @RequestBody 注解就行。
                    method: method, // alias for type. version added: 1.9.0
                    type: method,  // prior to 1.9.0.
                    dataType: type,
                    data: data,
                    success: callback,
                    beforeSend: function (xhr) {
                        this.layerIndex = layui.layer.load(2, { shade: [0.5, '#393D49'] });
                        var csrfHeader = $("meta[name='_csrf_header']").attr('content');
                        var csrf = $("meta[name='_csrf']").attr('content');
                        if (csrfHeader && csrf) {
                            xhr.setRequestHeader(csrfHeader, csrf);
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
                        let message = 'Unknown error!';
                        switch (xhr.status) {
                            case (400):
                                message = '客户端异常，请检查你的输入！<br>Client Error! Please check your input!';
                                if (xhr.responseJSON.message) {
                                    message = xhr.responseJSON.message;
                                }
                                layui.layer.msg(message, { icon: 5, time: 0, closeBtn: 1, title: xhr.status });
                                break;
                            case (401):
                                message = '未登录或者会话已过期！<br>No login or Session is invalid!';
                                layui.layer.msg(message, { icon: 2, title: xhr.status });
                                // 刷新当前页面
                                top.location.reload(true);
                                break;
                            case (403):
                                message = '无权限！No permission!';
                                layui.layer.msg(message, { icon: 2, title: xhr.status });
                                break;
                            case (404):
                                message = '找不到资源！Not Found!';
                                layui.layer.msg(message, { icon: 2, title: xhr.status });
                                break;
                            case (408):
                                message = '请求超时！Request timeout!';
                                layui.layer.msg(message, { icon: 2, title: xhr.status });
                                break;
                            case (500):
                                message = '服务器异常！Server Exception!';
                                layui.layer.msg(message, { icon: 2, title: xhr.status });
                                break;
                            default:
                                message = '未知异常，请联系系统管理员！<br>Unknown error, please contact your administrator!';
                                layui.layer.msg(message, { icon: 2, title: xhr.status });
                                break;
                        }
                    }
                }, jQuery.isPlainObject(url) && url));
            };
        });
    }
    exports(MOD_NAME, vitality);
});

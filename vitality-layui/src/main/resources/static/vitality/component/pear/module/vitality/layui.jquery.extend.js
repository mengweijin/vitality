/**
 * 在 layui 之后引入。
 * 1、layui.use() 内部使用：let $ = layui.$; $.vtl.form.disableAll('#form');
 * 2、layui.use() 外部直接使用：layui.$.vtl.form.disableAll('#form');
 */
layui.use(['jquery'], function () {
    let $ = layui.$, jQuery = layui.jquery;

    $.extend({
        "vtl": {
            /**
             * 获取 Layui checkbox 的提交值。
             * selector: 如：
             * 根据 lay-filter 和 checkbox 的 name 属性选择：'[lay-filter=operate-filter] input:checkbox[name=hobby]:checked'
             * 根据 formId 和 checkbox 的 name 属性选择：'form input:checkbox[name=hobby]:checked'
             * @return 示例：[1,2,3,4]
             */
            getCheckBoxCheckedValue: function (selector) {
                let value = [];
                $(selector).each(function () {
                    value.push($(this).val());
                });
                // return value.join(",");
                return value;
            },

            /**
             * 禁用所有表单元素。$.vtl.form.disableAll('#form');
             * *：选择元素下所有元素；
             * @param selector jquery选择器，示例：#form
             */
            disableAll: function (selector) {
                $(selector + " *").attr("disabled", true);
            },

            tableReloadData: function(tableId, field = {}, deep = false) {
                if(field && typeof field === 'boolean') {
                    deep = field;
                    field = {};
                }
                layui.table.reloadData(tableId, { where: field }, deep);
            },

            /**
             * 从layer弹层本身关闭自己。（从父页面获取值 parent.$('#父页面元素id').val();）
             * 注意：window.parent和parent是有区别的
             */
            closeLayer: function () {
                //先得到当前iframe层的索引
                let index = parent.layui.layer.getFrameIndex(window.name);
                //再执行关闭弹出层
                parent.layui.layer.close(index);
                // 重载整个父页面
                // parent.layui.location.reload();
            },

            /**
             * type: 0（信息框，默认）1（页面层）2（iframe层）3（加载层）4（tips层）
             * closeBtn: layer提供了两种风格的关闭按钮，可通过配置1和2来展示，如果不显示，则closeBtn: 0。默认为1
             * @param {String} content Mandatory. 普通文本、html 或者一个 url（For Example: /sys/user/1?id=1）
             * @param {Object} options Optional. 如：{ title: '详情', area: ['100%', '100%'] }
             * @returns 
             */
            openLayer: function(content, options = {}) {
                //设置参数的默认值
                let defaults = {
                    title: "",
                    type: 2,
                    shade: [0.5, "#393D49"],
                    closeBtn: 1,
                    shadeClose: false,
                    maxmin: true,
                    resize: false,
                    area: ['800px', '450px']
                };
                options = $.extend(defaults, options);
                options.content = content;
                let index = layui.layer.open(options);
                return index;
            },

            /**
             * 图片裁剪。注意：回调函数 success 和 yes 的三个参数顺序是不一样的！！！
             * @param {Function} success 回调函数。可以在这里实现初始图片回显，把图片通过 that 对象设置进去。
             *      function(layero, index, that){
             *          // 弹层的最外层元素的 jQuery 对象
             *          console.log(layero);
             *          // 弹层的索引值
             *          console.log(index);
             *          // 弹层内部原型链中的 this --- 2.8+
             *          console.log(that);
             *      }
             * @param {Function} yes 回调函数。可以在这里实现裁剪后的图片保存，通过 that 对象获取图片的 base64 字符串。
             *      function(index, layero, that){
             *          // 获取 iframe 中 body 元素的 jQuery 对象
             *          var body = layer.getChildFrame('body', index);
             *          // 给 iframe 页中的某个输入框赋值
             *          body.find('input').val('Hello layer.');
             *      }
             * @returns
             */
            openCopper: function(success, yes) {
                let url = this.getRootPath() + '/component/html/cropper.html';
                return this.openLayer(url, {
                    title: '图片裁剪',
                    area: ['900px', '500px'],
                    btn: ['保存', '取消'],
                    success: success,
                    yes: yes
                });
            },

            openUserSelector: function(success, yes, multiple = true) {
                let url = $.vtl.getRootPath() + '/views/system/user/userTableSelector.html?multiple=' + multiple;
                return this.openLayer(url, {
                    title: '选择用户',
                    area: ['80%', '90%'],
                    btn: ['保存', '取消'],
                    success: success,
                    yes: yes
                });
            },

            /**
             * type {String} 可选 role, dept, post, user
             * id {Long} 对应 type 的表记录 ID
             */
            openMenuSelector: function(success, yes, type, id) {
                let params = this.buildQueryVariable({ type: type, id: id });
                let url = $.vtl.getRootPath() + '/views/system/menu/menuAuthorizationList.html?' + params;
                return this.openLayer(url, {
                    title: '选择权限',
                    area: ['70%', '90%'],
                    btn: ['保存', '取消'],
                    success: success,
                    yes: yes
                });
            },

            /**
             * 判断是否是空
             * @param value
             */
            isBlank: function(val) {
                if(val == null || undefined) {
                    return true;
                } else {
                    // 1. 替换所有空白字符（不是空格，比如，回车换行符等）; 2. trim() 去前后空格
                    val = val.toString().replace(/\s/g, "").trim();
                    return val == "" || val == "undefined" || val == "null";
                }
            },

            /**
             * 判断是否是空
             * @param value
             */
            blankToDefault: function(val, defaultVal) {
                return this.isBlank(val) ? defaultVal : val;
            },

            /**
             * js字节单位转换函数（KB MB GB TB PB EB ZB）
             * @param value
             */
            byteConvert: function(bytes) {
                 if (isNaN(bytes)) {
                     return '';
                 }
                 let symbols = ['bytes', 'KB', 'MB', 'GB', 'TB', 'PB', 'EB', 'ZB', 'YB'];
                 let exp = Math.floor(Math.log(bytes)/Math.log(2));
                 if (exp < 1) {
                     exp = 0;
                 }
                 let i = Math.floor(exp / 10);
                 bytes = bytes / Math.pow(2, 10 * i);

                 if (bytes.toString().length > bytes.toFixed(2).toString().length) {
                     bytes = bytes.toFixed(2);
                 }
                 return bytes + ' ' + symbols[i];
             },

             secondsFormat: function (seconds) {
                let daySec = 24 *  60 * 60;
                let hourSec=  60 * 60;
                let minuteSec = 60;
                let dd = Math.floor(seconds / daySec);
                let hh = Math.floor((seconds % daySec) / hourSec);
                let mm = Math.floor((seconds % hourSec) / minuteSec);
                let ss = seconds % minuteSec;
                if(dd > 0) {
                    return dd + "天" + hh + "小时" + mm + "分钟" + ss + "秒";
                } else if (hh > 0) {
                    return hh + "小时" + mm + "分钟" + ss + "秒";
                } else if (mm > 0) {
                    return mm + "分钟" + ss + "秒";
                } else {
                    return ss + "秒";
                }
             },

            /**
             * 复制文本到剪切板。text是复制文本
             */
            copyToClip: function(text) {
                let el = document.createElement('textarea')
                el.value = text
                document.body.appendChild(el)
                // 选中input元素的文本
                el.select()
                // 复制内容到剪贴板
                document.execCommand('copy')
                document.body.removeChild(el)
                layui.layer.msg("COPY SUCCESS!")
            },
            /**
             *  let dataList = [
             *      { "id": "1", "parentId": "0", "name": "xxx", "age": "22" },
             *      { "id": "2", "parentId": "1", "name": "yyy", "age": "15" },
             *      { "id": "3", "parentId": "1", "name": "zzz", "age": "12" },
             *      { "id": "4", "parentId": "0", "name": "zzz", "age": "23" },
             *      { "id": "5", "parentId": "4", "name": "zzz", "age": "34" }
             *  ];
             * 
             * let result = quickboot.groupBy(dataList, 'parentId');
             *
             * @param {Array} dataList 
             * @param {String} groupKey 
             */
            groupBy: function (dataList, groupKey) {
                return dataList.reduce(function (result, current) {
                    result[current[groupKey]] = result[current[groupKey]] || [];
                    result[current[groupKey]].push(current);
                    return result;
                }, {})
            },
    
            /**
             *  let dataList = [
             *      { "id": "1", "parentId": "0", "name": "xxx", "age": "22" },
             *      { "id": "2", "parentId": "1", "name": "yyy", "age": "15" },
             *      { "id": "3", "parentId": "1", "name": "zzz", "age": "12" },
             *      { "id": "4", "parentId": "0", "name": "zzz", "age": "23" },
             *      { "id": "5", "parentId": "4", "name": "zzz", "age": "34" }
             *  ];
             * 
             *  let result = $.vtl.tree(dataList, '0');
             * 
             * @param {Array} dataList Mandatory. 
             * @param {String} rootId Mandatory. For Example: '0'
             * @param {String} idFieldName Optional, default 'id'
             * @param {String} parentFieldName Optional, default 'parentId'
             * @param {String} childrenNodeName Optional, default 'children'
             * @returns 
             */
            tree: function (dataList, rootId, idFieldName = 'id', parentFieldName = 'parentId', childrenNodeName = 'children') {
                let groupedList = this.groupBy(dataList, parentFieldName);
                for (let i in dataList) {
                    let row = dataList[i];
                    row[childrenNodeName] = groupedList[row[idFieldName]];
                }
                return groupedList[rootId];
            },

            getQueryVariable: function (variable) {
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
            },
            buildQueryVariable: function (data) {
                let url = '';
                for(let i in data) {
                    url += '&' + i + '=' + encodeURIComponent(data[i]);
                }
                return url.substring(1);
            },

            /**
             * @returns 前端项目根路径。比如：/vitality
             */
            getRootPath: function() {
                let rootPath = layui.sessionData('vitality').rootPath;
                return this.blankToDefault(rootPath, '');
            },
            /**
             * @returns 后台接口代理根路径。比如：/api
             */
            getApiRootPath: function() {
                let apiRootPath = layui.sessionData('vitality').apiRootPath;
                return this.blankToDefault(apiRootPath, '');
            },

            extendJqueryAjaxMethod: function() {
                if($.hasOwnProperty('delete')) {
                    return;
                }
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

                jQuery["sync"] = function (method, url, data, callback, type) {
                    // shift arguments if data argument was omitted
                    if (jQuery.isFunction(data)) {
                        type = type || callback;
                        callback = data;
                        data = undefined;
                    }
                    return jQuery.ajax(jQuery.extend({
                        url: url,
                        async: false,
                        method: method, // alias for type. version added: 1.9.0
                        type: method,  // prior to 1.9.0.
                        dataType: type,
                        data: data,
                        success: callback
                    }, jQuery.isPlainObject(url) && url));
                };
            },

            /**
            * 设置 jquery ajax 全局配置
            */
            setJqueryAjaxSetup: function(apiRootPath = '') {
                let this_ = this;

                $.ajaxSetup({
                    layerIndex: -1,
                    cache: false,
                    async: true,
                    //contentType: 'application/json;charset=UTF-8', // default is: application/x-www-form-urlencoded
                    //data: JSON.stringify(data) // contentType 为 json, 就要这样处理，太麻烦了，所以还是不要用了。使用 application/x-www-form-urlencoded 只需要 Controller 请求方法参数不要添加 @RequestBody 注解就行。
                    beforeSend: function (xhr) {
                        this.layerIndex = layui.layer.load(2, { shade: [0.5, '#393D49'] });
                        var csrfHeader = $("meta[name='_csrf_header']").attr("content");
                        var csrf = $("meta[name='_csrf']").attr("content");
                        if (csrfHeader && csrf) {
                            xhr.setRequestHeader(csrfHeader, csrf);
                        }
                        if(!this_.isBlank(apiRootPath) && !(this.url.indexOf('http') == 0)) {
                            let slash = this.url.indexOf('/') == 0 ? '' : '/';
                            this.url = apiRootPath + slash + this.url;
                        }
                    },
                    error: function (xhr, textStatus, errorThrown) {
                        let message = 'Unknown error!';
                        if (xhr.responseJSON && xhr.responseJSON.message) {
                            message = xhr.responseJSON.message;
                        }
                        switch (xhr.status) {
                            case (400):
                                message = message + "<br>客户端操作异常，请检查你的输入或操作！<br>Client Error! Please check your input or operation!";
                                layui.layer.msg(message, { icon: 5, time: 0, closeBtn: 1, title: xhr.status });
                                break;
                            case (401):
                                message = '未登录或者会话已过期！<br>No login or Session was invalid!';
                                //layui.layer.msg(message, { icon: 2, title: xhr.status });
                                //top.location.reload(true);    // 刷新当前页面
                                top.window.location.href = $.vtl.getRootPath() + '/login.html';
                                //top.window.open($.vtl.getRootPath() + '/login.html', '_self');
                                break;
                            case (403):
                                layui.layer.msg("无权限！No permission!", { icon: 2, title: xhr.status });
                                break;
                            case (404):
                                layui.layer.msg(message + "<br>找不到资源！Not Found!", { icon: 2, title: xhr.status });
                                break;
                            case (408):
                                layui.layer.msg("请求超时！Request timeout!", { icon: 2, title: xhr.status });
                                break;
                            case (500):
                                layui.layer.msg(message + "<br>服务器异常！Server Exception!", { icon: 2, time: 0, closeBtn: 1, title: xhr.status });
                                break;
                            default:
                                message = message + "<br>未知异常，请联系系统管理员！<br>Unknown error, please contact your administrator!";
                                layui.layer.msg(message, { icon: 2, time: 0, closeBtn: 1, title: xhr.status });
                                break;
                        }
                    },        
                    // 请求完成时运行的函数（在请求成功或失败之后均调用，即在 success 和 error 函数之后）。
                    complete: function (xhr, textStatus) {
                        layui.layer.close(this.layerIndex);
                        if(this.url.endsWith("/login")) {
                            return;
                        } else if (this.type.toUpperCase() != 'GET' && xhr.status == 200) {
                            layui.layer.msg('操作成功！Operate successfully!', { icon: 1, time: 800 });
                        }
                        if(xhr.status == 401 && !this.url.endsWith("/login.html")) {
                            top.window.location.href = $.vtl.getRootPath() + '/login.html';
                        }
                    },
                });
            }
        }
    });


    $.vtl.extendJqueryAjaxMethod();
    $.vtl.setJqueryAjaxSetup($.vtl.getApiRootPath());

});

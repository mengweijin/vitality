/**
 * 在 layui 之后引入。
 * 1、layui.use() 内部使用：let $ = layui.$; $.form.disableAll('#form');
 * 2、layui.use() 外部直接使用：layui.$.form.disableAll('#form');
 */
layui.use(['jquery'], function () {
    let $ = layui.$, jQuery = layui.jquery;

    $.extend({
        "form": {
            /**
             * 获取 Layui checkbox 的提交值。
             * selector: 如：
             * 根据 lay-filter 和 checkbox 的 name 属性选择：'[lay-filter=operate-filter] input:checkbox[name=hobby]:checked'
             * 根据 formId 和 checkbox 的 name 属性选择：'form input:checkbox[name=hobby]:checked'
             * @return 示例：[1,2,3,4]
             */
            getCheckedCheckBoxValue: function (selector) {
                let value = [];
                $(selector).each(function () {
                    value.push($(this).val());
                });
                // return value.join(",");
                return value;
            },

            /**
             * 禁用所有表单元素。$.form.disableAll('#form');
             * *：选择元素下所有元素；
             * @param selector jquery选择器，示例：#form
             */
            disableAll: function (selector) {
                $(selector + " *").attr("disabled", true);
            },
        },

        "table": {
            /**
             * 
             * @param {String} id tableId Mandatory. 
             * @param {Object} where Optional
             * @param {Boolean} deep Optional. 是否采用深度重载（即参数深度克隆，也就是重载时始终携带初始时及上一次重载时的参数），默认 false
             */
            reloadData: function (tableId, where = {}, deep = false) {
                if(typeof where === 'boolean') {
                    deep = where;
                    where = {};
                }
                layui.table.reloadData(
                    tableId, 
                    { 
                        where: where, 
                        page: { theme: '#1E9FFF', curr: 1 } 
                    }, 
                    deep)
            },
            reloadParentData: function (tableId) {
                parent.layui.table.reloadData(tableId, { page: { theme: '#1E9FFF', curr: 1 } }, true)
            }
        },

        "layer": {
            /**
             * 从layer弹层本身关闭自己。（从父页面获取值 parent.$('#父页面元素id').val();）
             * 注意：window.parent和parent是有区别的
             */
            close: function () {
                //先得到当前iframe层的索引
                let index = parent.layui.layer.getFrameIndex(window.name);
                //再执行关闭弹出层
                parent.layui.layer.close(index);
                // 重载整个父页面
                // parent.layui.location.reload();
            },

            /**
             * type: 0（信息框，默认）1（页面层）2（iframe层）3（加载层）4（tips层）
             * @param {String} content Mandatory. 普通文本、html 或者一个 url（For Example: /sys/user/1?id=1）
             * @param {Object} options Optional. 如：{ title: '详情', area: ['100%', '100%'] }
             * @returns 
             */
            open: function(content, options = {}) {
                //设置参数的默认值
                let defaults = {
                    title: "",
                    type: 2,
                    shade: [0.5, "#393D49"],
                    shadeClose: true,
                    maxmin: true,
                    resize: false,
                    area: ['800px', '450px']
                };
                options = $.extend(defaults, options);
                options.content = content;
                let index = layui.layer.open(options);
                return index;
            }
        },

        "tab": {
            open: function(filter, title, url, id = Date.now()) {
                // 防止重复打开
                let $titleLiElements = window.top.layui.$("[lay-filter=" + filter + "] ul.layui-tab-title li");
                for (let i = 0; i < $titleLiElements.length; i++) {
                    if ($titleLiElements.eq(i).attr('lay-id') == id) {
                        window.top.layui.element.tabChange(filter, id);
                        return;
                    }
                }

                window.top.layui.element.tabAdd(filter, {
                    title: title
                    , content: '<iframe src="' + url + '" scrolling="auto" frameborder="no" width="100%" height="100%"></iframe>'
                    , id: id
                });
                window.top.layui.element.tabChange(filter, id);
                window.top.layui.$("[lay-filter=" + filter + "] div.layui-tab-content div.layui-show").css("height", "100%");

                if ($titleLiElements.length > 10) {
                    let nth2Layid = window.top.layui.$("[lay-filter=" + filter + "] ul.layui-tab-title li:nth-child(2)").attr("lay-id");
                    window.top.layui.element.tabDelete(filter, nth2Layid);
                }

                //let $currentTabTitle = $("[lay-filter=" + filter + "] ul.layui-tab-title li[lay-id=" + id + "]");
                //$currentTabTitle.mouseenter(function(e) {
                    //let currentLayid = $(this).attr('lay-id');
                    //判断菜单的位置 
                    //let $popupMenu = $("#main_tab_right_menu").hide();
                    //left = ($(document).width() - e.pageX) < $popupMenu.width() ? (e.pageX - $popupMenu.width()) : e.pageX;
                    //top = ($(document).height() - e.pageY) < $popupMenu.height() ? (e.pageY - $popupMenu.height()) : e.pageY;
                    //$popupMenu.css({left: left - 240, top: top}).show();
                //}).mouseout(function() {
                    //$("#main_tab_right_menu").hide();
                //});
            },
            closeThis: function(filter, id) {
                window.top.layui.element.tabDelete(filter, id);
            },
            closeLeft: function(filter, id) {
                let $titleLiElements = window.top.layui.$("[lay-filter=" + filter + "] ul.layui-tab-title li");
                for (let i = 1; i < $titleLiElements.length; i++) {
                    if ($titleLiElements.eq(i).attr('lay-id') == id) {
                        return;
                    } else {
                        window.top.layui.element.tabDelete(filter, $titleLiElements.eq(i).attr('lay-id'));
                    }
                }
            },
            closeRight: function(filter, id) {
                let $titleLiElements = window.top.layui.$("[lay-filter=" + filter + "] ul.layui-tab-title li");
                let currentIndex = null;
                for (let i = 1; i < $titleLiElements.length; i++) {
                    if(currentIndex && i > currentIndex) {
                        window.top.layui.element.tabDelete(filter, $titleLiElements.eq(i).attr('lay-id'));
                    } else if ($titleLiElements.eq(i).attr('lay-id') == id) {
                        currentIndex = i;
                    }
                }
            },
            closeOther: function(filter, id) {
                let $titleLiElements = window.top.layui.$("[lay-filter=" + filter + "] ul.layui-tab-title li");
                for (let i = 1; i < $titleLiElements.length; i++) {
                    if ($titleLiElements.eq(i).attr('lay-id') != id) {
                        window.top.layui.element.tabDelete(filter, $titleLiElements.eq(i).attr('lay-id'));
                    }
                }
            },
            closeAll: function(filter) {
                let $titleLiElements = window.top.layui.$("[lay-filter=" + filter + "] ul.layui-tab-title li");
                for (let i = 1; i < $titleLiElements.length; i++) {
                    window.top.layui.element.tabDelete(filter, $titleLiElements.eq(i).attr('lay-id'));
                }
            }
        },

        "iframe": {
            refresh: function(id, url) {
                document.getElementById(id).setAttribute('src', url);
            }
        },

        "util": {
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
                layui.layer.msg("Copy SUCCESS!")
            },
            /**
             * http://localhost:8080/user?i=1&j=2
             * location.search的值就是 "?i=1&j=2" 
             * substr(1)   就是从索引1开始截取 
             * 所以以这个页面为例的话 location.search.substring(1) 的值就是 "i=1&j=2"
             * 使用：$.util.getUrlParamValue('i'); 结果返回：1
             * @param {String} name param name 
             */
            getUrlParamValue: function (name) {
                let reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
                let matchedArray = decodeURI(window.location.search.substring(1)).match(reg);
                return matchedArray == null ? null : matchedArray[2];
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
             *  let result = qb.tree(dataList, '0');
             * 
             * @param {Array} dataList Mandatory. 
             * @param {String} rootId Mandatory. For Example: '0'
             * @param {String} idFieldName Optional, default 'id'
             * @param {String} parentFieldName Optional, default 'parentId'
             * @param {String} childrenNodeName Optional, default 'children'
             * @returns 
             */
            tree: function (dataList, rootId, idFieldName = 'id', parentFieldName = 'parentId', childrenNodeName = 'children') {
                let groupedList = quickboot.groupBy(dataList, parentFieldName);
                for (let i in dataList) {
                    let row = dataList[i];
                    row[childrenNodeName] = groupedList[row[idFieldName]];
                }
                return groupedList[rootId];
            },
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


